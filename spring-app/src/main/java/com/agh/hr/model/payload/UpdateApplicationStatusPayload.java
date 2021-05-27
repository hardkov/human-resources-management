package com.agh.hr.model.payload;

import javax.validation.constraints.NotNull;
import com.agh.hr.persistence.model.Status;
import lombok.Value;

@Value
public class UpdateApplicationStatusPayload {
    @NotNull(message = "Cannot be null")
    Long applicationId;

    @NotNull(message = "Cannot be null")
    Status status;
}
