package com.back.gaon.domain.auth.dto;

import com.back.gaon.domain.member.enums.Role;

/**
 * 로그인 응답 DTO
 */
public record LoginResponse(
        String accessToken,      // JWT 액세스 토큰
        String refreshToken,     // JWT 리프레시 토큰 (옵션)
        Long memberId,           // 회원 ID
        String name,             // 이름
        Role role                // 역할 (STUDENT, PARENT, ADMIN, DIRECTOR)
) {}