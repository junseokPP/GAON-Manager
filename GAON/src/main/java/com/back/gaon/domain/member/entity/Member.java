package com.back.gaon.domain.member.entity;

import com.back.gaon.domain.member.enums.Gender;
import com.back.gaon.domain.member.enums.MemberStatus;
import com.back.gaon.domain.member.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * 공통 회원 정보
 * - 학생, 학부모, 관리자, 원장 모두 이 테이블 사용
 * - role로 구분
 */
@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;  // 🔥 새로 추가

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @Column(length = 255)
    private String password;  // 🔥 비밀번호 (암호화 필수)

    @Column(name = "password_temp")
    private Boolean passwordTemp;  // 🔥 임시 비밀번호 여부 (첫 로그인 시 변경 강제)

    @Column(name = "setup_token", length = 100)
    private String setupToken;  // 🔥 학부모 비밀번호 설정 토큰

    @Column(name = "setup_token_expired_at")
    private java.time.LocalDateTime setupTokenExpiredAt;  // 토큰 만료 시간

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberStatus status;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    // 학생/학부모/관리자별 상세 정보는 별도 테이블로
    // StudentDetail (1:1)
}