package com.back.gaon.domain.attendance.service;

import com.back.gaon.domain.attendance.dto.response.TodayAttendanceDashboardResponse;
import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    Attendance adminCheckIn(Long scheduleId);

    Attendance adminCheckOut(Long scheduleId);

    // 선택: 배치에서 결석 처리할 때 사용
    void markAbsentIfNotCheckedIn(Schedule schedule);

    List<TodayAttendanceDashboardResponse> getTodayDashboard(LocalDate date);
}