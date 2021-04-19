package com.agh.hr.persistence.DTO;

import com.agh.hr.persistence.model.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDTO {
    protected Long id;
    protected PersonalData personalData;
    protected String position;
}
