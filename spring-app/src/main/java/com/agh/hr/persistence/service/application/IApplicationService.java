package com.agh.hr.persistence.service.application;

import com.agh.hr.model.payload.BonusApplicationPayload;
import com.agh.hr.model.payload.DelegationApplicationPayload;
import com.agh.hr.model.payload.LeaveApplicationPayload;
import com.agh.hr.persistence.dto.BonusApplicationDTO;
import com.agh.hr.persistence.dto.DelegationApplicationDTO;
import com.agh.hr.persistence.dto.LeaveApplicationDTO;
import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IApplicationService {
    List<LeaveApplicationDTO> getLeaveApplications(UserDTO user);

    List<BonusApplicationDTO> getBonusApplications(UserDTO user);

    List<DelegationApplicationDTO> getDelegationApplications(UserDTO user);

    Optional<LeaveApplicationDTO> getLeaveApplicationById(Long id);

    Optional<BonusApplicationDTO> getBonusApplicationById(Long id);

    Optional<DelegationApplicationDTO> getDelegationApplicationById(Long id);

    DelegationApplicationDTO createDelegationApplication(UserDTO user, DelegationApplicationPayload payload);

    LeaveApplicationDTO createLeaveApplication(UserDTO user, LeaveApplicationPayload payload);

    BonusApplicationDTO createBonusApplication(UserDTO user, BonusApplicationPayload payload);
}
