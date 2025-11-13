package com.back.gaon.domain.schedule.service.template;

import com.back.gaon.domain.schedule.dto.request.template.ScheduleTemplateCreateRequest;
import com.back.gaon.domain.schedule.dto.response.template.ScheduleTemplateCreateResponse;
import com.back.gaon.domain.schedule.dto.response.template.ScheduleTemplateDetailResponse;

import java.util.List;

public interface ScheduleTemplateService {
    ScheduleTemplateCreateResponse create(ScheduleTemplateCreateRequest req /*, Authentication auth */);
    ScheduleTemplateDetailResponse findById(Long id);
    List<ScheduleTemplateDetailResponse> findAll();
}
