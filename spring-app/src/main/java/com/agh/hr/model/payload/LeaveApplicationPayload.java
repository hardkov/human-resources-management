package com.agh.hr.model.payload;

import com.agh.hr.persistence.dto.validation.groups.AfterNullCheck;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@GroupSequence({LeaveApplicationPayload.class, AfterNullCheck.class})
public class LeaveApplicationPayload extends ApplicationPayload {
    @NotNull(message = "Start date must be provided")
    private LocalDate startDate;

    @NotNull(message = "End date must be provided")
    private LocalDate endDate;

    private boolean paid;

    @AssertTrue(message = "Start date cannot be after end date", groups = {AfterNullCheck.class})
    private boolean isValidDates(){
        return startDate.minusDays(1).isBefore(endDate);
    }
}
