// com.back.gaon.domain.outing.dto.response.OutingResponse

package com.back.gaon.domain.outing.dto.response;

import com.back.gaon.domain.outing.enums.OutingReasonType;
import com.back.gaon.domain.outing.enums.OutingStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutingResponse {

    private Long outingId;
    private Long memberId;
    private Long scheduleId;

    private LocalDateTime outAt;
    private LocalDateTime returnAt;

    private OutingStatus status;
    private OutingReasonType reasonType;

    private boolean excused;   // 사전 승인/정당 사유 여부
    private String reason;     // 상세 메모
}