package com.back.gaon.domain.member.controller;

import com.back.gaon.domain.member.dto.MemberResponse;
import com.back.gaon.domain.member.dto.MemberUpdateRequest;
import com.back.gaon.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
public class MeController {

    private final MemberService memberService;

    /** 내 정보 조회 */
    @GetMapping
    public ResponseEntity<MemberResponse> getMyInfo(
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    /** 내 정보 수정 (이름, 학교, 전화번호 등 일부 변경 가능) */
    @PutMapping
    public ResponseEntity<MemberResponse> updateMyInfo(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid MemberUpdateRequest request
    ) {
        return ResponseEntity.ok(memberService.updateMember(memberId, request));
    }
}