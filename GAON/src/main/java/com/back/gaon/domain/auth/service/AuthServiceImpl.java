package com.back.gaon.domain.auth.service;

import com.back.gaon.domain.auth.dto.LoginRequest;
import com.back.gaon.domain.auth.dto.LoginResponse;
import com.back.gaon.domain.auth.dto.ParentPasswordSetupRequest;
import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.repository.MemberRepository;
import com.back.gaon.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final SmsService smsService;

    @Override
    public LoginResponse login(LoginRequest request) {

        Member member = memberRepository.findByPhone(request.phone())
                .orElseThrow(() -> new RuntimeException("회원 없음"));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        String token = jwtProvider.generateAccessToken(member);

        return new LoginResponse(
                token,
                null,
                member.getId(),
                member.getName(),
                member.getRole()
        );
    }

    @Override
    public LoginResponse refresh(String refreshToken) {

        // 1. RefreshToken 검증
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("RefreshToken invalid");
        }

        // 2. 유저 ID 꺼냄
        Long memberId = jwtProvider.getMemberId(refreshToken);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. 새 AccessToken 발급
        String newAccessToken = jwtProvider.generateAccessToken(member);

        return new LoginResponse(
                newAccessToken,
                refreshToken,  // 필요하면 새로 발급도 가능
                member.getId(),
                member.getName(),
                member.getRole()
        );
    }
    @Override
    public void setupParentPassword(ParentPasswordSetupRequest request) {

        // 1. setupToken으로 부모 계정 찾기
        Member parent = memberRepository.findBySetupToken(request.token())
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

        // 2. 전화번호 일치 확인
        if (!parent.getPhone().equals(request.phone())) {
            throw new RuntimeException("전화번호가 일치하지 않습니다.");
        }

        // 3. SMS 인증 코드 검증 (예시: smsService.verify() 사용 가정)
        boolean smsValid = smsService.verifyCode(request.phone(), request.smsCode());
        if (!smsValid) {
            throw new RuntimeException("SMS 인증번호가 올바르지 않습니다.");
        }

        // 4. 새 비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(request.newPassword());
        parent.setPassword(encodedPassword);

        // 5. setupToken은 한 번 사용하면 폐기 (보안)
        parent.setSetupToken(null);

        // 6. 저장
        memberRepository.save(parent);
    }
}