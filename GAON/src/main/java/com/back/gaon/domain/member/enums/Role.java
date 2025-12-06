package com.back.gaon.domain.member.enums;

/**
 * 회원 역할
 * - STUDENT: 학생 (독서실 이용자)
 * - PARENT: 학부모 (자녀 모니터링)
 * - ADMIN: 관리자 (독서실 운영 직원)
 * - DIRECTOR: 원장 (최고 관리자)
 */
public enum Role {
    STUDENT,    // 학생
    PARENT,     // 학부모
    ADMIN,      // 관리자
    DIRECTOR    // 원장
}