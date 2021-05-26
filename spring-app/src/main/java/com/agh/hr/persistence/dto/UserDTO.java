package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.dto.validation.groups.UpdateGroup;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserDTO {
    @NotNull(groups = {UpdateGroup.class}, message = "Cannot be null")
    @Min(0)
    protected Long id;

    @Valid
    protected PersonalDataDTO personalData;

    protected String position;

}
