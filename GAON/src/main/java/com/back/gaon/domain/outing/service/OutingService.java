// com.back.gaon.domain.outing.service.OutingService

package com.back.gaon.domain.outing.service;

import com.back.gaon.domain.outing.entity.Outing;
import com.back.gaon.domain.outing.enums.OutingReasonType;

public interface OutingService {

    // 외출 시작 (QR 찍어서 나갈 때)
    Outing startOuting(Long scheduleId, Long memberId,
                       OutingReasonType reasonType,
                       boolean excused,
                       String memo);

    // 외출 복귀 (QR 찍고 들어올 때)
    Outing endOuting(Long scheduleId, Long memberId);
}