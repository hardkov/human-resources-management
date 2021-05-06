package com.agh.hr.persistence.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserDTO {

    protected Long id;
    protected PersonalDataDTO personalData;
    protected String position;

}
