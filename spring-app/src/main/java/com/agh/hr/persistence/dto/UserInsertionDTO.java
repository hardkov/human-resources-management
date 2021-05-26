package com.agh.hr.persistence.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserInsertionDTO {
    private Long id;

    @NotBlank(message = "Cannot be blank")
    private String username;

    @NotNull
    @Size(min = 8, message = "Must be at least 8 characters long")
    private String password;

    @Valid
    private PersonalDataDTO personalData;

    private String position;
}
