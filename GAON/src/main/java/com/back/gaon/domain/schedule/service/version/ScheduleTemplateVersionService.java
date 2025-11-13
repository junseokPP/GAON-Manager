package com.back.gaon.domain.schedule.service.version;

import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionCreateResponse;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionDetailResponse;

import java.util.List;

public interface ScheduleTemplateVersionService {
    ScheduleTemplateVersionCreateResponse create(ScheduleTemplateVersionCreateRequest req /*, Authentication auth */);
    ScheduleTemplateVersionDetailResponse findVersionById(Long id);
    List<ScheduleTemplateVersionDetailResponse> findByTemplateId(Long templateId);
    ScheduleTemplateVersionDetailResponse findByTemplateAndId(Long templateId, Long versionId);
}