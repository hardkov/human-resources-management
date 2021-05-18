package com.agh.hr.model.payload;

import com.agh.hr.persistence.model.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ApplicationPayload {
    private String content;
    private String place;
    private Status status;
}
