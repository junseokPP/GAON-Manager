// com.back.gaon.domain.outing.service.OutingServiceImpl

package com.back.gaon.domain.outing.service;

import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.repository.AttendanceRepository;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.outing.enums.OutingReasonType;
import com.back.gaon.domain.outing.enums.OutingStatus;
import com.back.gaon.domain.outing.repository.OutingRepository;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;
import com.back.gaon.domain.schedule.schedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OutingServiceImpl implements OutingService {

    private final OutingRepository outingRepository;
    private final ScheduleRepository scheduleRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public Outing startOuting(Long scheduleId, Long memberId,
                              OutingReasonType reasonType,
                              boolean excused,
                              String memo) {

        // 1) 스케줄 조회
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 스케줄이 없습니다. id=" + scheduleId));

        // 2) 스케줄의 학생과 요청 memberId 일치 확인
        if (!schedule.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("스케줄과 학생이 일치하지 않습니다.");
        }

        // 3) 이미 외출 중인지 확인 (마지막 외출이 OUT 상태면 막기)
        outingRepository.findTop1ByMemberIdAndScheduleIdOrderByOutAtDesc(memberId, scheduleId)
                .ifPresent(last -> {
                    if (last.getReturnAt() == null && last.getStatus() == OutingStatus.OUT) {
                        throw new IllegalStateException("이미 외출 중입니다.");
                    }
                });

        // 4) 출석 정보(있으면 연결, 없어도 null 허용)
        Attendance attendance = attendanceRepository
                .findByScheduleIdAndMemberId(scheduleId, memberId)
                .orElse(null);

        // 5) 외출 생성
        Outing outing = Outing.builder()
                .member(schedule.getMember())
                .schedule(schedule)
                .attendance(attendance)
                .outAt(LocalDateTime.now())
                .returnAt(null)
                .status(OutingStatus.OUT)
                .reasonType(reasonType)
                .excused(excused)
                .reason(memo)
                .build();

        return outingRepository.save(outing);
    }

    @Override
    public Outing endOuting(Long scheduleId, Long memberId) {

        // 1) 마지막 외출 가져오기
        Outing outing = outingRepository
                .findTop1ByMemberIdAndScheduleIdOrderByOutAtDesc(memberId, scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("외출 기록이 없습니다."));

        if (outing.getReturnAt() != null) {
            throw new IllegalStateException("이미 복귀 처리된 외출입니다.");
        }

        // 2) 복귀 시간 세팅
        LocalDateTime now = LocalDateTime.now();
        outing.setReturnAt(now);

        // 🔥 지금은 단순히 RETURNED로 두고,
        // 나중에 "복귀 지각", "반일 조퇴" 등의 규칙을 붙일 때 LATE_RETURN 로직 추가
        outing.setStatus(OutingStatus.RETURNED);

        return outing;
    }
}