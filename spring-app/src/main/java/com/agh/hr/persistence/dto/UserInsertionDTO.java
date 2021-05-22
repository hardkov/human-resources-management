package com.agh.hr.persistence.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserInsertionDTO {
    private Long id;
    private String username;
    private String password;
    private PersonalDataDTO personalData;
    private String position;
}
