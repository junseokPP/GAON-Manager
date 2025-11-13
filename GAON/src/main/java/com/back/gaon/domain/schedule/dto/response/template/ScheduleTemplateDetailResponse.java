package com.back.gaon.domain.schedule.dto.response.template;

import com.back.gaon.domain.schedule.enums.TemplateStatus;

import java.time.LocalDateTime;

public record ScheduleTemplateDetailResponse(
        Long id,
        Long memberId,
        String name,
        String description,
        TemplateStatus status,
        Long approvedBy,
        LocalDateTime approvedAt,
        Long currentApprovedVersionId
) {}