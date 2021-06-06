package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.dto.validation.groups.UpdateGroup;
import com.agh.hr.persistence.model.ContractType;
import com.agh.hr.persistence.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ContractDTO {
    @NotNull(groups = {UpdateGroup.class}, message = "Id cannot be null")
    private Long id;
    private UserDTO user;

    @NotNull(message = "Cannot be null")
    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull(message = "Type cannot be null")
    private ContractType contractType;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero(message = "Salary cannot be negative")
    private BigDecimal baseSalary;

    @AssertTrue(message = "Start date cannot be after end date")
    private boolean isValidDates(){
        return endDate == null || startDate.minusDays(1).isBefore(endDate);
    }
}
