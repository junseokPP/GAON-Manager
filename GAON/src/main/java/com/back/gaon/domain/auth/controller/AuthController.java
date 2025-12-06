package com.back.gaon.domain.auth.controller;

import com.back.gaon.domain.auth.dto.LoginRequest;
import com.back.gaon.domain.auth.dto.LoginResponse;
import com.back.gaon.domain.auth.dto.ParentPasswordSetupRequest;
import com.back.gaon.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 컨트롤러
 * - 로그인, 학부모 비밀번호 설정 등
 * /api/v1/auth
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 (모든 역할: 학생, 학부모, 관리자, 원장)
     * POST /api/v1/auth/login
     * 
     * Request:
     * {
     *   "phone": "010-1234-5678",
     *   "password": "mypassword123"
     * }
     * 
     * Response:
     * {
     *   "accessToken": "eyJhbGc...",
     *   "memberId": 1,
     *   "name": "홍길동",
     *   "role": "STUDENT"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 학부모 비밀번호 설정
     * POST /api/v1/auth/parent/setup-password
     * 
     * 흐름:
     * 1. 관리자가 학생 등록 → 학부모 계정 자동 생성 → setupToken 발급
     * 2. 학부모에게 SMS 발송 (링크 포함)
     * 3. 학부모가 링크 클릭 → 본인 인증 → 비밀번호 설정
     * 
     * Request:
     * {
     *   "token": "abc-123-def-456",
     *   "phone": "010-9999-8888",
     *   "smsCode": "123456",
     *   "newPassword": "mypassword123!"
     * }
     */
    @PostMapping("/parent/setup-password")
    public ResponseEntity<Void> setupParentPassword(
            @RequestBody @Valid ParentPasswordSetupRequest request
    ) {
        authService.setupParentPassword(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그아웃 (옵션 - JWT는 서버에서 무효화 못하므로 클라이언트에서 토큰 삭제)
     * POST /api/v1/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String token
    ) {
        // TODO: 블랙리스트 처리 (Redis 등)
        return ResponseEntity.ok().build();
    }

    /**
     * 토큰 갱신 (옵션)
     * POST /api/v1/auth/refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @RequestHeader("Authorization") String refreshToken
    ) {
        LoginResponse result = authService.refresh(refreshToken);
        return ResponseEntity.ok(result);
    }
}