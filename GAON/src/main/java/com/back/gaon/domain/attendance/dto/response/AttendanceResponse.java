package com.back.gaon.domain.attendance.dto.response;

import com.back.gaon.domain.attendance.enums.AttendanceSource;
import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AttendanceResponse {

    private Long attendanceId;
    private Long scheduleId;
    private Long memberId;

    private LocalDate attendanceDate;

    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;

    private AttendanceStatus status;
    private AttendanceSource source;

    private boolean excused;
    private String memo;
}