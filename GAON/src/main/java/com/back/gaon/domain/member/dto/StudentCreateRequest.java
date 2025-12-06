package com.back.gaon.domain.member.dto;

import com.back.gaon.domain.member.enums.Gender;
import com.back.gaon.domain.member.enums.Grade;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {

    private String name;

    private String phone;

    private Gender gender;

    private String school;

    private Grade grade;

    private String parentPhone;   // 학부모 자동 생성에 사용됨

    private String emergencyContact;

    private String memo;

    private String seatNumber;

    private LocalDate registrationDate;
}

