// src/main/java/com/back/gaon/domain/schedule/controller/ScheduleTemplateVersionController.java
package com.back.gaon.domain.schedule.controller;

import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionCreateRequest;
import com.back.gaon.domain.schedule.dto.request.version.ScheduleTemplateVersionRejectRequest;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionCreateResponse;
import com.back.gaon.domain.schedule.dto.response.version.ScheduleTemplateVersionDetailResponse;
import com.back.gaon.domain.schedule.service.version.ScheduleTemplateVersionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule-template-versions")
public class ScheduleTemplateVersionController {

    private final ScheduleTemplateVersionService versionService;

    @PostMapping
    // @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<ScheduleTemplateVersionCreateResponse> create(
            @Valid @RequestBody ScheduleTemplateVersionCreateRequest req
            // , Authentication auth
    ) {
        ScheduleTemplateVersionCreateResponse created = versionService.create(req /*, auth */);
        return ResponseEntity.created(URI.create("/api/schedule-template-versions/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleTemplateVersionDetailResponse> get(@PathVariable Long id) {
        ScheduleTemplateVersionDetailResponse getVersion = versionService.findVersionById(id);
        return ResponseEntity.ok(getVersion);
    }

    /**  버전 승인 */
    @PostMapping("/{id}/approve")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleTemplateVersionDetailResponse> approve(@PathVariable Long id) {
        ScheduleTemplateVersionDetailResponse approved = versionService.approve(id);
        return ResponseEntity.ok(approved);
    }

    /**  버전 반려 */
    @PostMapping("/{id}/reject")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleTemplateVersionDetailResponse> reject(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleTemplateVersionRejectRequest req
    ) {
        ScheduleTemplateVersionDetailResponse rejected =
                versionService.reject(id, req.rejectReason());
        return ResponseEntity.ok(rejected);
    }
}