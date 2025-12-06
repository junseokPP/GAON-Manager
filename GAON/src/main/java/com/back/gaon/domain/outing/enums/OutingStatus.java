// com.back.gaon.domain.outing.enums.OutingStatus
package com.back.gaon.domain.outing.enums;

public enum OutingStatus {
    OUT,          // 외출 중
    RETURNED,     // 정상 복귀
    LATE_RETURN   // 늦게 복귀(벌점 대상 가능)
}