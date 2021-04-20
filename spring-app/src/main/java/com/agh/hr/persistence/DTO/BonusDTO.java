package com.agh.hr.persistence.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class BonusDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amount;
    private BonusApplicationDTO bonusApplication;
}
