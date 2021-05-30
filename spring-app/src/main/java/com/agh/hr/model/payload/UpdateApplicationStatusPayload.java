package com.agh.hr.model.payload;

import javax.validation.constraints.NotNull;
import com.agh.hr.persistence.model.Status;
import lombok.Value;

@Value
public class UpdateApplicationStatusPayload {
    @NotNull(message = "Id cannot be null")
    Long applicationId;

    @NotNull(message = "Status cannot be null")
    Status status;
}
