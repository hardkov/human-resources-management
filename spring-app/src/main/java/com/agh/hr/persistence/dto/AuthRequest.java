package com.agh.hr.persistence.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @NotBlank(message = "Cannot be blank")
    @Email(message = "Must be valid email")
    private String username;

    @NotBlank(message = "Cannot be blank")
    private String password;
}
