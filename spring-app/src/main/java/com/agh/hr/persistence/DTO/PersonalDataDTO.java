package com.agh.hr.persistence.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonalDataDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birthdate;
    private byte[] thumbnail;
}
