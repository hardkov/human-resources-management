package com.agh.hr.persistence.dto;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PersonalDataDTO {
    private Long id;

    @NotBlank(message = "Cannot be blank")
    private String firstname;

    @NotBlank(message = "Cannot be blank")
    private String lastname;

    @NotBlank(message = "Cannot be blank")
    @Email(message = "Must be valid email")
    private String email;

    @NotBlank(message = "Cannot be blank")
    private String phoneNumber;

    private String address;

    @NotNull(message = "Cannot be null")
    @Past(message = "Must be date in the past")
    private LocalDate birthdate;

    private byte[] thumbnail;

}
