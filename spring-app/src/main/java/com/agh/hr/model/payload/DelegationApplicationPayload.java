package com.agh.hr.model.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class DelegationApplicationPayload extends ApplicationPayload {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String destination;

}
