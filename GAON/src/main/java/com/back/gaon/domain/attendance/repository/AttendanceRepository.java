package com.back.gaon.domain.attendance.repository;

import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // 스케줄+학생 하나당 출석 1개 (유니크)
    Optional<Attendance> findByScheduleIdAndMemberId(Long scheduleId, Long memberId);

    boolean existsByScheduleIdAndMemberId(Long scheduleId, Long memberId);

    // 날짜 기준 조회 (오늘 출석 리스트 등)
    List<Attendance> findByAttendanceDate(LocalDate date);

    // 특정 학생의 기간별 출석 조회
    List<Attendance> findByMemberIdAndAttendanceDateBetween(Long memberId, LocalDate start, LocalDate end);

    // 날짜 + 상태별 (예: 오늘 지각자 목록)
    List<Attendance> findByAttendanceDateAndStatus(LocalDate date, AttendanceStatus status);

}