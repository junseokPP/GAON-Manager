package com.back.gaon.domain.schedule.dto.request.item;

import java.time.LocalTime;

public record ItemUpsertDto(
        Long id,
        String title,
        String description,
        LocalTime startTime,
        LocalTime endTime
) {}