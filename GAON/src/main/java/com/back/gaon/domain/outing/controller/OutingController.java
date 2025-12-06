// com.back.gaon.domain.outing.controller.OutingController

package com.back.gaon.domain.outing.controller;

import com.back.gaon.domain.outing.dto.response.OutingResponse;
import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.outing.enums.OutingReasonType;
import com.back.gaon.domain.outing.service.OutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/outings")
@RequiredArgsConstructor
public class OutingController {

    private final OutingService outingService;

    /**
     * 학생(또는 키오스크): 외출 시작
     *
     * 예)
     * POST /api/v1/outings/start?scheduleId=1&memberId=1&reasonType=ACADEMY&excused=false&memo=학원
     */
    @PostMapping("/start")
    public ResponseEntity<OutingResponse> startOuting(
            @RequestParam Long scheduleId,
            @RequestParam Long memberId,
            @RequestParam OutingReasonType reasonType,
            @RequestParam(defaultValue = "false") boolean excused,
            @RequestParam(required = false) String memo
    ) {
        Outing outing = outingService.startOuting(scheduleId, memberId, reasonType, excused, memo);
        return ResponseEntity.ok(toDto(outing));
    }

    /**
     * 학생(또는 키오스크): 외출 복귀
     *
     * 예)
     * POST /api/v1/outings/end?scheduleId=1&memberId=1
     */
    @PostMapping("/end")
    public ResponseEntity<OutingResponse> endOuting(
            @RequestParam Long scheduleId,
            @RequestParam Long memberId
    ) {
        Outing outing = outingService.endOuting(scheduleId, memberId);
        return ResponseEntity.ok(toDto(outing));
    }

    // ====== 엔티티 -> DTO 변환 ======
    private OutingResponse toDto(Outing o) {
        return OutingResponse.builder()
                .outingId(o.getId())
                .memberId(o.getMember().getId())
                .scheduleId(o.getSchedule().getId())
                .outAt(o.getOutAt())
                .returnAt(o.getReturnAt())
                .status(o.getStatus())
                .reasonType(o.getReasonType())
                .excused(o.isExcused())
                .reason(o.getReason())
                .build();
    }
}