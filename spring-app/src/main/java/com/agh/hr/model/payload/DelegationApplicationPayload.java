package com.agh.hr.model.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DelegationApplicationPayload extends ApplicationPayload {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String destination;

    @AssertTrue(message = "Start date cannot be after end date")
    private boolean isValidDates(){
        return startDate.minusDays(1).isBefore(endDate);
    }
}
