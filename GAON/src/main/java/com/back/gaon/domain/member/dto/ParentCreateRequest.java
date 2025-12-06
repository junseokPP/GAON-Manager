package com.back.gaon.domain.member.dto;

import com.back.gaon.domain.member.enums.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentCreateRequest {

    private String name;

    private String phone;

    private Gender gender;

    private String email;
}