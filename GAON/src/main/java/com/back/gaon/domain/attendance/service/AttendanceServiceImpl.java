package com.back.gaon.domain.attendance.service;

import com.back.gaon.domain.attendance.dto.response.TodayAttendanceDashboardResponse;
import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.enums.AttendanceSource;
import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import com.back.gaon.domain.attendance.repository.AttendanceRepository;
import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.outing.repository.OutingRepository;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.schedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ScheduleRepository scheduleRepository;
    private final OutingRepository outingRepository;

    // 지각 인정 유예 시간 (분)
    private static final int LATE_GRACE_MINUTES = 5;

    @Override
    public Attendance adminCheckIn(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("스케줄을 찾을 수 없습니다. id=" + scheduleId));

        Member member = schedule.getMember();
        LocalDateTime now = LocalDateTime.now();

        Attendance attendance = attendanceRepository
                .findByScheduleIdAndMemberId(schedule.getId(), member.getId())
                .orElseGet(() -> createNewAttendance(schedule, member));

        if (attendance.isCheckedIn()) {
            return attendance;
        }

        attendance.setCheckInAt(now);
        attendance.setAttendanceDate(getAttendanceDateFromSchedule(schedule));

        LocalDateTime startAt = getScheduleStartDateTime(schedule);
        if (now.isAfter(startAt.plusMinutes(LATE_GRACE_MINUTES))) {
            attendance.setStatus(AttendanceStatus.LATE);
        } else {
            attendance.setStatus(AttendanceStatus.PRESENT);
        }

        attendance.setSource(AttendanceSource.GAON_MANUAL);
        if (attendance.getId() == null) {
            attendance.setExcused(false);
        }

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance adminCheckOut(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("스케줄을 찾을 수 없습니다. id=" + scheduleId));

        Member member = schedule.getMember();

        Attendance attendance = attendanceRepository
                .findByScheduleIdAndMemberId(schedule.getId(), member.getId())
                .orElseThrow(() -> new EntityNotFoundException("등원 기록이 없습니다. 먼저 등원을 입력하세요."));

        if (attendance.isCheckedOut()) {
            return attendance;
        }

        attendance.setCheckOutAt(LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }

    @Override
    public void markAbsentIfNotCheckedIn(Schedule schedule) {
        Member member = schedule.getMember();
        boolean exists = attendanceRepository.existsByScheduleIdAndMemberId(schedule.getId(), member.getId());
        if (exists) {
            return;
        }

        Attendance absent = Attendance.builder()
                .schedule(schedule)
                .member(member)
                .attendanceDate(getAttendanceDateFromSchedule(schedule))
                .status(AttendanceStatus.ABSENT)
                .source(AttendanceSource.GAON_AUTO)
                .excused(false)
                .build();

        attendanceRepository.save(absent);
    }

    // === private helpers ===

    private Attendance createNewAttendance(Schedule schedule, Member member) {
        return Attendance.builder()
                .schedule(schedule)
                .member(member)
                .attendanceDate(getAttendanceDateFromSchedule(schedule))
                .status(AttendanceStatus.PRESENT)
                .source(AttendanceSource.GAON_MANUAL)
                .excused(false)
                .build();
    }

    private LocalDate getAttendanceDateFromSchedule(Schedule schedule) {
        return getScheduleStartDateTime(schedule).toLocalDate();
        // 또는 schedule에 LocalDate date 필드가 있으면:
        // return schedule.getDate();
    }

    private LocalDateTime getScheduleStartDateTime(Schedule schedule) {
        return LocalDateTime.of(schedule.getDate(), schedule.getStartTime());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodayAttendanceDashboardResponse> getTodayDashboard(LocalDate date) {

        // 1) 오늘 스케줄 전체 조회 (하루당 1개)
        List<Schedule> schedules = scheduleRepository.findByDate(date);

        return schedules.stream()
                .map(schedule -> {

                    Member member = schedule.getMember();

                    // 2) 해당 학생의 출석(있을 수도/없을 수도)
                    Optional<Attendance> optAttendance =
                            attendanceRepository.findByScheduleIdAndMemberId(
                                    schedule.getId(),
                                    member.getId()
                            );

                    // 3) 해당 학생의 외출 기록 중 마지막 것
                    Optional<Outing> optLastOuting =
                            outingRepository.findTop1ByMemberIdAndScheduleIdOrderByOutAtDesc(
                                    member.getId(),
                                    schedule.getId()
                            );

                    // 외출 상태 계산
                    boolean outingNow = false;
                    String reason = null;
                    LocalDateTime outAt = null;
                    LocalDateTime returnAt = null;

                    if (optLastOuting.isPresent()) {
                        Outing last = optLastOuting.get();
                        outAt = last.getOutAt();
                        returnAt = last.getReturnAt();
                        reason = last.getReasonType().name();
                        outingNow = (last.getReturnAt() == null);  // 아직 복귀 안 했음
                    }

                    // Attendance 관련 값
                    AttendanceStatus status = null;
                    LocalDateTime in = null;
                    LocalDateTime out = null;
                    boolean excused = false;

                    if (optAttendance.isPresent()) {
                        Attendance att = optAttendance.get();
                        status = att.getStatus();
                        in = att.getCheckInAt();
                        out = att.getCheckOutAt();
                        excused = att.isExcused();
                    }

                    return TodayAttendanceDashboardResponse.builder()
                            .scheduleId(schedule.getId())
                            .memberId(member.getId())
                            .memberName(member.getName())
                            .date(schedule.getDate())
                            .startTime(schedule.getStartTime())
                            .endTime(schedule.getEndTime())
                            .attendanceStatus(status)
                            .checkInAt(in)
                            .checkOutAt(out)
                            .excused(excused)
                            .outingNow(outingNow)
                            .lastOutingReason(reason)
                            .lastOutAt(outAt)
                            .lastReturnAt(returnAt)
                            .build();

                }).toList();
    }
}