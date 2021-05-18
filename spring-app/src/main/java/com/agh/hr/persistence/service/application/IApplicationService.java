package com.agh.hr.persistence.service.application;

import com.agh.hr.model.payload.BonusApplicationPayload;
import com.agh.hr.model.payload.DelegationApplicationPayload;
import com.agh.hr.model.payload.LeaveApplicationPayload;
import com.agh.hr.persistence.dto.BonusApplicationDTO;
import com.agh.hr.persistence.dto.DelegationApplicationDTO;
import com.agh.hr.persistence.dto.LeaveApplicationDTO;
import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;

public interface IApplicationService {
    List<LeaveApplicationDTO> getLeaveApplications(UserDTO user);

    List<BonusApplicationDTO> getBonusApplications(UserDTO user);

    List<DelegationApplicationDTO> getDelegationApplications(UserDTO user);

    DelegationApplicationDTO createDelegationApplication(UserDTO user, DelegationApplicationPayload payload);

    LeaveApplicationDTO createLeaveApplication(UserDTO user, LeaveApplicationPayload payload);

    BonusApplicationDTO createBonusApplication(UserDTO user, BonusApplicationPayload payload);
}
