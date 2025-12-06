package com.back.gaon.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 관리자/원장 상세 정보 (선택사항)
 * - Member(role=ADMIN or DIRECTOR)와 1:1 관계
 * - 직원 관리가 필요한 경우에만 사용
 */
@Entity
@Table(name = "admin_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDetail {

    @Id
    private Long id;  // Member의 PK를 그대로 사용

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "hire_date")
    private LocalDate hireDate;  // 입사일

    @Column(name = "resign_date")
    private LocalDate resignDate;  // 퇴사일 (null이면 재직중)

    @Column(length = 50)
    private String department;  // 담당 업무 (상담, 관리, 청소 등)

    @Column(name = "authority_level")
    private Integer authorityLevel;  // 권한 레벨 (1: 일반, 2: 수석, 3: 원장)

    @Column(name = "working_hours", length = 50)
    private String workingHours;  // 근무 시간 (예: 09:00-18:00)

    @Column(length = 255)
    private String memo;  // 특이사항
}