package com.agh.hr.model.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class BonusApplicationPayload extends ApplicationPayload {

    private BigDecimal money;
}
