package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.dto.validation.groups.UpdateGroup;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class LeaveDTO {
    @NotNull(groups = {UpdateGroup.class}, message = "Cannot be null")
    private Long id;
    private UserDTO user;

    @NotNull(message = "Cannot be null")
    private LocalDate startDate;

    @NotNull(message = "Cannot be null")
    private LocalDate endDate;
    private boolean paid;
    private byte[] thumbnail;

    @AssertTrue(message = "Start date cannot be after end date")
    private boolean isValidDates(){
        return startDate.minusDays(1).isBefore(endDate);
    }
}
