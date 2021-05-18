package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.model.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class ApplicationDTO {
    private long id;
    private String content;
    private UserDTO user;
    private String place;
    private LocalDateTime date;
    private Status status;
}
