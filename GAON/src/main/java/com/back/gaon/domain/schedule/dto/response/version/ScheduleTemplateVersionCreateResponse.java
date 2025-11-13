package com.back.gaon.domain.schedule.dto.response.version;

import com.back.gaon.domain.schedule.enums.TemplateStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScheduleTemplateVersionCreateResponse(
        Long id,
        Long templateId,
        Integer versionNo,
        TemplateStatus status
) {}