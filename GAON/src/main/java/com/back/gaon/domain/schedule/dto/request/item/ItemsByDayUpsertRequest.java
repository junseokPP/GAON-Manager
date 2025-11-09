package com.back.gaon.domain.schedule.dto.request.item;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public record ItemsByDayUpsertRequest(
        Map<DayOfWeek, List<ItemUpsertDto>> itemsByDay
) {}