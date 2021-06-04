package com.agh.hr.persistence.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LeaveApplicationDTO extends ApplicationDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean paid;

    @AssertTrue(message = "Start date cannot be after end date")
    private boolean isValidDates(){
        return startDate.minusDays(1).isBefore(endDate);
    }
}
