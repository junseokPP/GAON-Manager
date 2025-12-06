package com.back.gaon.domain.member.dto;

import com.back.gaon.domain.member.enums.Grade;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentUpdateRequest {

    private String name;

    private String school;

    private Grade grade;

    private String seatNumber;

    private String emergencyContact;

    private String memo;

}
