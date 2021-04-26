package com.agh.hr.persistence.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContractDTO {
    private Long id;
    private UserDTO user;
    private PersonalDataDTO personalData;
    private String position;
}
