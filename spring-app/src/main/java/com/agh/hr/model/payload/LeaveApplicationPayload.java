package com.agh.hr.model.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class LeaveApplicationPayload extends ApplicationPayload {

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean paid;

}
