package com.agh.hr.persistence.DTO;

import com.agh.hr.persistence.model.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDetailedDTO extends UserDTO {
    private List<Permission> permissions;
    private List<Leave> leaves;
    private List<Contract> contracts;
    private List<Bonus> bonuses;
    private List<Delegation> delegations;
    private List<Application> applications;
}
