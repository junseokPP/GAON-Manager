package com.back.gaon.domain.attendance.entity;

import com.back.gaon.domain.attendance.enums.AttendanceSource;
import com.back.gaon.domain.attendance.enums.AttendanceStatus;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;
import com.back.gaon.global.base.BaseTimeEntity;
import com.back.gaon.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_attendance_schedule_member",
                columnNames = {"schedule_id", "member_id"}
        ),
        indexes = {
                @Index(name = "idx_attendance_member_date", columnList = "member_id, attendance_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 스케줄에 대한 출석인지
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    // 어떤 학생의 출석인지
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 조회/통계용 출석 날짜 (스케줄 날짜 복사해서 넣을 예정)
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    // 등원 시간 (관리자가 입력)
    private LocalDateTime checkInAt;

    // 하원 시간 (관리자가 입력)
    private LocalDateTime checkOutAt;

    // 출석 상태 (정상, 지각, 결석)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttendanceStatus status;

    // 이 데이터가 어디서 왔는지
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttendanceSource source;

    // 사전 연락 등으로 "정당한" 지각/결석인지 여부
    // true면 벌점에서 제외하거나 따로 처리 가능
    @Column(nullable = false)
    private boolean excused;

    @Column(length = 255)
    private String memo;

    // 편의 메서드
    public boolean isCheckedIn() {
        return checkInAt != null;
    }

    public boolean isCheckedOut() {
        return checkOutAt != null;
    }
}