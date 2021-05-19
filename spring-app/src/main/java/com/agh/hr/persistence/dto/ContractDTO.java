package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContractDTO {
    private Long id;
    private UserDTO user;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractType contractType;
    private BigDecimal baseSalary;
}
