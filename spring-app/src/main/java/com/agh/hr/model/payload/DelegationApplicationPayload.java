package com.agh.hr.model.payload;

import com.agh.hr.persistence.dto.validation.groups.AfterNullCheck;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@GroupSequence({DelegationApplicationPayload.class, AfterNullCheck.class})
public class DelegationApplicationPayload extends ApplicationPayload {
    @NotNull(message = "Start date must be provided")
    private LocalDateTime startDate;

    @NotNull(message = "End date must be provided")
    private LocalDateTime endDate;
    private String destination;

    @AssertTrue(message = "Start date cannot be after end date", groups = AfterNullCheck.class)
    private boolean isValidDates(){
        return startDate.minusDays(1).isBefore(endDate);
    }
}
