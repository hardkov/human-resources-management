package com.agh.hr.persistence.DTO;

import com.agh.hr.persistence.model.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDTO {
    private Long id;
    private PersonalData personalData;
    private String position;
}
