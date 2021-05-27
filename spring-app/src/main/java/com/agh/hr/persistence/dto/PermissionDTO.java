package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.dto.validation.groups.UpdateGroup;
import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.model.User;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PermissionDTO {
    @NotNull(groups = {UpdateGroup.class}, message = "Cannot be null")
    private Long id;
    private UserDTO user;

    @NotNull(message = "Cannot be null")
    private List<Long> write;

    @NotNull(message = "Cannot be null")
    private List<Long> read;
    private boolean add;
}