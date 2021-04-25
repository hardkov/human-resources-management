package com.agh.hr.persistence.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LeaveApplicationDTO extends ApplicationDTO{
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean paid;
}
