package com.agh.hr.persistence.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContractDTO {
    protected Long id;
    protected PersonalDataDTO personalData;
    protected String position;
}
