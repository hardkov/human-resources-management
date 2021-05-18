package com.agh.hr.persistence.service.application;

import com.agh.hr.model.error.NotAuthorizedException;
import com.agh.hr.model.payload.UpdateApplicationStatusPayload;
import com.agh.hr.persistence.dto.BonusApplicationDTO;
import com.agh.hr.persistence.dto.DelegationApplicationDTO;
import com.agh.hr.persistence.dto.LeaveApplicationDTO;
import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;

public interface ISupervisorApplicationService {

    List<LeaveApplicationDTO> getLeaveApplications(UserDTO supervisor) throws NotAuthorizedException;

    List<BonusApplicationDTO> getBonusApplications(UserDTO supervisor) throws NotAuthorizedException;

    List<DelegationApplicationDTO> getDelegationApplications(UserDTO supervisor) throws NotAuthorizedException;

    LeaveApplicationDTO updateLeaveApplicationStatus(UserDTO supervisor, UpdateApplicationStatusPayload payload) throws NotAuthorizedException;

    BonusApplicationDTO updateBonusApplicationStatus(UserDTO supervisor, UpdateApplicationStatusPayload payload) throws NotAuthorizedException;

    DelegationApplicationDTO updateDelegationApplicationStatus(UserDTO supervisor, UpdateApplicationStatusPayload payload) throws NotAuthorizedException;

}
