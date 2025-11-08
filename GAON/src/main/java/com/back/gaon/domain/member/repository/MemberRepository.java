package com.back.gaon.domain.member.repository;

import com.back.gaon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 학생 휴대폰 중복 여부 확인 (Create 시)
    boolean existsByPhone(String phone);

    // 학부모 휴대폰 중복 여부 확인 (Create 시)
    boolean existsByParentPhone(String parentPhone);

    // 학생 휴대폰 중복 여부 확인 (Update 시 — 본인 제외)
    boolean existsByPhoneAndIdNot(String phone, Long id);

    // 학부모 휴대폰 중복 여부 확인 (Update 시 — 본인 제외)
    boolean existsByParentPhoneAndIdNot(String parentPhone, Long id);
}