package com.agh.hr.persistence.DTO;
import lombok.*;


@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDTO {
    protected Long id;
    protected PersonalDataDTO personalData;
    protected String position;
}
