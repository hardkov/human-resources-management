package com.agh.hr.persistence.DTO;

import com.agh.hr.persistence.model.DelegationApplication;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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
