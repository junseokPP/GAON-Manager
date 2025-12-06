package com.back.gaon.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 로그인 요청 DTO
 */
public record LoginRequest(
        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식: 010-XXXX-XXXX")
        String phone,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {}