
package com.back.gaon.domain.auth.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public boolean verifyCode(String phone, String code) {
        // Redis 또는 DB에 저장한 인증번호와 비교하는 로직
        return true; // 예시
    }
}