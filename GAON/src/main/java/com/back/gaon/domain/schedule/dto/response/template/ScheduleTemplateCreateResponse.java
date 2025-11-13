package com.back.gaon.domain.schedule.dto.response.template;

public record ScheduleTemplateCreateResponse(
        Long id,
        Long memberId,
        String name,
        String description
) {}
