package com.agh.hr.model.error.payload;

import com.agh.hr.persistence.model.Status;
import lombok.Value;

@Value
public class UpdateApplicationStatusPayload {
    Long applicationId;
    Status status;
}
