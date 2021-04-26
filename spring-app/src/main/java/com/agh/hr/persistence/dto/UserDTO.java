package com.agh.hr.persistence.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDTO {
    protected Long id;
    protected PersonalDataDTO personalData;
    protected String position;
}
