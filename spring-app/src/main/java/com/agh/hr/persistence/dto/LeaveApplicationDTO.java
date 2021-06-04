package com.agh.hr.persistence.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LeaveApplicationDTO extends ApplicationDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean paid;
}
