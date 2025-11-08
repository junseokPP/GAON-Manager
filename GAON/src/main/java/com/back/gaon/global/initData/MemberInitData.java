package com.back.gaon.global.initData;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.enums.Gender;
import com.back.gaon.domain.member.enums.Grade;
import com.back.gaon.domain.member.enums.MemberStatus;
import com.back.gaon.domain.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class MemberInitData {

    @Bean
    CommandLineRunner initMembers(MemberRepository memberRepository) {
        return args -> {
            if (memberRepository.count() == 0) {

                Member m1 = createMember("홍길동", Gender.Male, "010-1111-2222", "010-9999-8888", "가온고등학교", Grade.High1);
                Member m2 = createMember("이영희", Gender.Female, "010-2222-3333", "010-1234-5678", "가온여자고등학교", Grade.High2);
                Member m3 = createMember("박철수", Gender.Male, "010-3333-4444", "010-8765-4321", "가온고등학교", Grade.High3);
                Member m4 = createMember("최지우", Gender.Female, "010-4444-5555", "010-5555-6666", "가온고등학교", Grade.High1);
                Member m5 = createMember("정민수", Gender.Male, "010-5555-7777", "010-7777-8888", "가온고등학교", Grade.High2);

                memberRepository.saveAll(java.util.List.of(m1, m2, m3, m4, m5));
            }
        };
    }

    // 🔹 회원 객체 생성 함수 (빌더 + 날짜 필드 추가)
    private Member createMember(String name, Gender gender, String phone, String parentPhone, String school, Grade grade) {
        LocalDate now = LocalDate.now();

        return Member.builder()
                .name(name)
                .gender(gender)
                .phone(phone)
                .parentPhone(parentPhone)
                .school(school)
                .grade(grade)
                .status(MemberStatus.ACTIVE)
                .createdAt(now)
                .joinDate(now)
                .build();
    }
}