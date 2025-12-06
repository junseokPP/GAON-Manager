package com.back.gaon.domain.auth.service;

import com.back.gaon.domain.auth.dto.LoginRequest;
import com.back.gaon.domain.auth.dto.LoginResponse;
import com.back.gaon.domain.auth.dto.ParentPasswordSetupRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    /**
     * 로그인
     * - 전화번호 + 비밀번호로 인증
     * - JWT 토큰 발급
     */
    LoginResponse login(LoginRequest request);
    LoginResponse refresh(String refreshToken);
    /**
     * 학부모 비밀번호 설정
     * - setupToken 검증
     * - SMS 인증 확인
     * - 비밀번호 암호화 후 저장
     */
    void setupParentPassword(ParentPasswordSetupRequest request);
}