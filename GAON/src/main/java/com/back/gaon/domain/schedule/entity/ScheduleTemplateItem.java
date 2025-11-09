package com.back.gaon.domain.schedule.entity;

import com.back.gaon.domain.schedule.enums.ScheduleBlockType;
import com.back.gaon.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.*;

/**
 * 주간 반복 규칙의 실제 일정 블록(요일·시간 단위)
 * - dayOfWeek: MONDAY~SUNDAY
 * - type: STUDY/ACADEMY/PERSONAL/OTHER
 * - startTime/endTime: 시작/종료 (end > start, 24:00은 내부 계산상 익일 00:00 처리 권장)
 * - description: 메모(선택)
 */
@Entity
@Table(name = "schedule_template_item",
        indexes = @Index(name = "idx_sti_version_day", columnList = "version_id, day_of_week"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ScheduleTemplateItem extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "version_id", nullable = false)
    private ScheduleTemplateVersion version;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 9)
    private DayOfWeek dayOfWeek;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ScheduleBlockType type;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Size(max = 100)
    private String description;
}
