package com.agh.hr.model.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class LeaveApplicationPayload extends ApplicationPayload {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean paid;

}
