package com.back.gaon.domain.attendance.controller;

import com.back.gaon.domain.attendance.dto.response.AttendanceResponse;
import com.back.gaon.domain.attendance.dto.response.TodayAttendanceDashboardResponse;
import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/attendances")
@RequiredArgsConstructor
public class AttendanceAdminController {

    // Impl 말고 인터페이스 타입으로 주입
    private final AttendanceService attendanceService;

    /**
     * 관리자: 등원 입력
     * 예) POST /api/admin/attendances/10/check-in
     *    -> scheduleId = 10번 스케줄 등원 처리
     */
    @PostMapping("/{scheduleId}/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(@PathVariable Long scheduleId) {
        Attendance attendance = attendanceService.adminCheckIn(scheduleId);
        return ResponseEntity.ok(toDto(attendance));
    }

    /**
     * 관리자: 하원 입력
     * 예) POST /api/admin/attendances/10/check-out
     */
    @PostMapping("/{scheduleId}/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(@PathVariable Long scheduleId) {
        Attendance attendance = attendanceService.adminCheckOut(scheduleId);
        return ResponseEntity.ok(toDto(attendance));
    }

    // ====== 엔티티 → DTO 변환 ======
    private AttendanceResponse toDto(Attendance attendance) {
        return AttendanceResponse.builder()
                .attendanceId(attendance.getId())
                .scheduleId(attendance.getSchedule().getId())
                .memberId(attendance.getMember().getId())
                .attendanceDate(attendance.getAttendanceDate())
                .checkInAt(attendance.getCheckInAt())
                .checkOutAt(attendance.getCheckOutAt())
                .status(attendance.getStatus())
                .source(attendance.getSource())
                .excused(attendance.isExcused())
                .memo(attendance.getMemo())
                .build();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<TodayAttendanceDashboardResponse>> getDashboard(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate target = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(attendanceService.getTodayDashboard(target));
    }
}