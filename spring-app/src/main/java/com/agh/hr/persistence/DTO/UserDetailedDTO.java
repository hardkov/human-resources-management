package com.agh.hr.persistence.DTO;

import com.agh.hr.persistence.model.*;
import jdk.jfr.Description;
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
@Deprecated
public class UserDetailedDTO extends UserDTO {
    private List<Permission> permissions;
    private List<LeaveDTO> leaves;
    private List<ContractDTO> contracts;
    private List<BonusDTO> bonuses;
    private List<DelegationDTO> delegations;
    private List<ApplicationDTO> applications;
}
