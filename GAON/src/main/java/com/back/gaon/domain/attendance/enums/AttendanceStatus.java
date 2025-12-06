package com.back.gaon.domain.attendance.enums;

public enum AttendanceStatus {
    PRESENT,   // 정상 등원
    LATE,      // 지각
    ABSENT     // 결석 (스케줄 끝날 때까지 미등원)
}