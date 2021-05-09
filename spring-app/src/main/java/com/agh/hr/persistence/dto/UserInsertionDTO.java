package com.agh.hr.persistence.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserInsertionDTO {
    private String username;
    private String password;
    private String role;
    private PersonalDataDTO personalData;
    private String position;
}
