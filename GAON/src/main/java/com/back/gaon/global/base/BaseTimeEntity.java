package com.back.gaon.global.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 공통 생성/수정 시각 관리용 상위 클래스
 * - createdAt: 최초 생성 시각
 * - updatedAt: 마지막 수정 시각
 */
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(nullable = false)
    protected LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
