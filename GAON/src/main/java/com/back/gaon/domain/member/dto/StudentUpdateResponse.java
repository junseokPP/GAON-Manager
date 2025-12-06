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
public class StudentUpdateResponse {

    private Long id;

    private String name;

    private String phone;

    private Gender gender;

    private String school;

    private Grade grade;

    private String seatNumber;

    private String emergencyContact;

    private String memo;

    private LocalDate updatedAt;  // 수정 일시
}
