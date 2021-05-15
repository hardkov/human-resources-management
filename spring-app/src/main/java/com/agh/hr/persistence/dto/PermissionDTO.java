package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.model.User;
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
    private Long id;
    private UserDTO user;
    private List<Long> write;
    private List<Long> read;
    private boolean add;
}