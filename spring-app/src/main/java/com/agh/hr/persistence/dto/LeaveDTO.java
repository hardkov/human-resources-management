package com.agh.hr.persistence.dto;

import lombok.*;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class LeaveDTO {
    private Long id;
    private UserDTO user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean paid;
    private byte[] thumbnail;
}
