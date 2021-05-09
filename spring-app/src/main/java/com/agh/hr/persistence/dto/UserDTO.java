package com.agh.hr.persistence.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDTO {
    private Long id;
    private PersonalDataDTO personalData;
    private String position;
}
