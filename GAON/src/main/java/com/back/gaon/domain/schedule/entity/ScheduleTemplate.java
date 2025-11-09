package com.back.gaon.domain.schedule.entity;

import com.back.gaon.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;

/**
 * "학생 1명당 1개" 권장되는 주간 스케줄의 논리 템플릿
 * - memberId: 소유 학생 식별자(Long)
 * - currentApprovedVersionId: 현재 운영 반영 중인 승인 버전 ID(옵셔널)
 * - versions: 이 템플릿의 모든 버전 목록(1:N)
 */
@Entity
@Table(
        name = "schedule_template",
        uniqueConstraints = @UniqueConstraint(name = "uk_schedule_template_member", columnNames = {"member_id"}),
        indexes = @Index(name = "idx_schedule_template_member", columnList = "member_id")
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ScheduleTemplate extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "current_approved_version_id")
    private Long currentApprovedVersionId;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<ScheduleTemplateVersion> versions = new ArrayList<>();
}

