package com.back.gaon.domain.attendance.dto.response;

import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class TodayAttendanceDashboardResponse {

    private Long scheduleId;

    private Long memberId;
    private String memberName;

    // 스케줄(하루 기본정보)
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    // 출석 정보(없으면 null)
    private AttendanceStatus attendanceStatus;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;
    private boolean excused;

    // 외출 정보
    private boolean outingNow;           // 지금 외출 중?
    private String lastOutingReason;     // 마지막 외출 사유
    private LocalDateTime lastOutAt;     // 외출 시작 시간
    private LocalDateTime lastReturnAt;  // 외출 복귀 시간(없으면 아직 밖)
}