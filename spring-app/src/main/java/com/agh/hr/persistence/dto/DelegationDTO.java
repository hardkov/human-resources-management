package com.agh.hr.persistence.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DelegationDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
    private DelegationApplicationDTO delegationApplication;
}
