package com.back.gaon.domain.schedule.service.version;

import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionCreateResponse;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionDetailResponse;
import com.back.gaon.domain.schedule.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.enums.TemplateStatus;
import com.back.gaon.domain.schedule.mapper.ScheduleTemplateMapper;
import com.back.gaon.domain.schedule.mapper.ScheduleTemplateVersionMapper;
import com.back.gaon.domain.schedule.repository.ScheduleTemplateRepository;
import com.back.gaon.domain.schedule.repository.ScheduleTemplateVersionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleTemplateVersionServiceImpl implements ScheduleTemplateVersionService {

    private final ScheduleTemplateVersionRepository scheduleTemplateVersionRepository;
    private final ScheduleTemplateRepository templateRepo;

    @Override
    public ScheduleTemplateVersionCreateResponse create(ScheduleTemplateVersionCreateRequest req /*, Authentication auth */) {
        // 1) 템플릿 존재 확인
        ScheduleTemplate template = templateRepo.findById(req.templateId())
                .orElseThrow(() -> new EntityNotFoundException("Template not found: id=" + req.templateId()));

        // 2) 동시에 PENDING 버전 1개만 허용 (권장 정책)
        if (Boolean.TRUE.equals(req.submit())
                && scheduleTemplateVersionRepository.existsByTemplateIdAndStatus(template.getId(), TemplateStatus.PENDING)) {
            throw new DataIntegrityViolationException("이미 승인 대기(PENDING) 중인 버전이 존재합니다.");
        }

        // 3) 버전 번호 결정 (요청이 null이면 자동 채번)
        int versionNo = (req.versionNo() != null)
                ? req.versionNo()
                : scheduleTemplateVersionRepository.findTopByTemplateIdOrderByVersionNoDesc(template.getId())
                .map(v -> v.getVersionNo() + 1)
                .orElse(1);

        // 4) 상태 결정: 지금은 학생 플로우만 (시큐리티 붙으면 관리자 즉시 APPROVED)
        TemplateStatus status = Boolean.TRUE.equals(req.submit())
                ? TemplateStatus.PENDING
                : TemplateStatus.DRAFT;

        // 5) 엔티티 생성/저장
        ScheduleTemplateVersion entity = ScheduleTemplateVersionMapper.toEntity(req, template, versionNo, status);
        ScheduleTemplateVersion saved = scheduleTemplateVersionRepository.save(entity);

        // 6) 응답
        return ScheduleTemplateVersionMapper.toCreateResponse(saved);
    }

    @Override
    public ScheduleTemplateVersionDetailResponse findVersionById(Long id){
        ScheduleTemplateVersion version = scheduleTemplateVersionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule template version not found: id=" + id));

        return ScheduleTemplateVersionMapper.toVersionDetailResponse(version);
    }

    @Override
    public List<ScheduleTemplateVersionDetailResponse> findByTemplateId(Long templateId) {
        List<ScheduleTemplateVersion> versions =
                scheduleTemplateVersionRepository.findByTemplateIdOrderByVersionNoDesc(templateId);

        return versions.stream()
                .map(ScheduleTemplateVersionMapper::toVersionDetailResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleTemplateVersionDetailResponse findByTemplateAndId(Long templateId, Long versionId) {
        ScheduleTemplateVersion version = scheduleTemplateVersionRepository.findById(versionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Schedule template version not found: id=" + versionId
                ));

        // 🔥 소속 검증: 이 버전이 정말 해당 템플릿의 것인지
        if (!version.getTemplate().getId().equals(templateId)) {
            // 템플릿-버전 조합이 잘못된 경우 → 404로 숨기는 게 더 자연스럽다
            throw new EntityNotFoundException(
                    "Schedule template version not found for templateId=" + templateId + ", versionId=" + versionId
            );
        }

        return ScheduleTemplateVersionMapper.toVersionDetailResponse(version);
    }
}