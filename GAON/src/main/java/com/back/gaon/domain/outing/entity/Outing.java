// com.back.gaon.domain.outing.entity.Outing

package com.back.gaon.domain.outing.entity;

import com.back.gaon.domain.attendance.entity.Attendance;
import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.outing.enums.OutingReasonType;
import com.back.gaon.domain.outing.enums.OutingStatus;
import com.back.gaon.domain.schedule.schedule.entity.Schedule;
import com.back.gaon.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "outing",
        indexes = {
                @Index(name = "idx_outing_member_schedule", columnList = "member_id, schedule_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outing extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 누가 외출하는지 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /** 어떤 하루 스케줄(09~24)에 속한 외출인지 */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    /** (선택) 어떤 출석과 연결되는지 - 나중에 벌점/리포트에 쓰고 싶으면 활용 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    /** 외출 시작/복귀 시간 */
    private LocalDateTime outAt;
    private LocalDateTime returnAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OutingStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OutingReasonType reasonType;

    /** 사전 승인 여부 (사전 연락 왔거나 허용된 외출인지) */
    @Column(nullable = false)
    private boolean excused;

    /** 상세 사유 메모 */
    @Column(length = 100)
    private String reason;
}