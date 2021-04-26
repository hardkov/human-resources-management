package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.model.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
