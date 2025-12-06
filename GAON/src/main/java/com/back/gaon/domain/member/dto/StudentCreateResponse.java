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
public class StudentCreateResponse {

    private Long studentId;

    private String name;

    private String phone;

    private Gender gender;

    private String school;

    private Grade grade;

    private String seatNumber;

    private LocalDate registrationDate;

    private Long parentId;

    private String parentPhone;

    private boolean parentCreated;   // 학부모 새로 생성 되었는지 여부
}
