package com.agh.hr.persistence.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LeaveDTO {
    private Long id;
    private UserDTO user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean paid;
    private byte[] thumbnail;
}
