package com.back.gaon.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 학부모 비밀번호 설정 요청 DTO
 */
public record ParentPasswordSetupRequest(
        @NotBlank(message = "토큰은 필수입니다.")
        String token,           // setupToken (SMS로 받은 링크의 토큰)

        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식: 010-XXXX-XXXX")
        String phone,           // 본인 확인용

        @NotBlank(message = "SMS 인증코드는 필수입니다.")
        @Size(min = 6, max = 6, message = "인증코드는 6자리입니다.")
        String smsCode,         // SMS 인증 코드

        @NotBlank(message = "새 비밀번호는 필수입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                message = "비밀번호는 8자 이상, 영문/숫자/특수문자 포함"
        )
        String newPassword
) {}