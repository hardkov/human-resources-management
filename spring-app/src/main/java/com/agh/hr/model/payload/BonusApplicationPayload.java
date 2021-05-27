package com.agh.hr.model.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BonusApplicationPayload extends ApplicationPayload {
    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be positive")
    private BigDecimal money;
}
