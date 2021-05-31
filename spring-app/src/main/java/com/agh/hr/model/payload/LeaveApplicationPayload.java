package com.agh.hr.model.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LeaveApplicationPayload extends ApplicationPayload {

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean paid;

    @AssertTrue(message = "Start date cannot be after end date")
    private boolean isValidDates(){
        return startDate.minusDays(1).isBefore(endDate);
    }
}
