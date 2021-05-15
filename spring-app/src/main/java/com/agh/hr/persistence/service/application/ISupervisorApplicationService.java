package com.agh.hr.persistence.service.application;

import com.agh.hr.persistence.dto.BonusApplicationDTO;
import com.agh.hr.persistence.dto.DelegationApplicationDTO;
import com.agh.hr.persistence.dto.LeaveApplicationDTO;
import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;

public interface ISupervisorApplicationService {

    List<LeaveApplicationDTO> getLeaveApplications(UserDTO supervisor);

    List<BonusApplicationDTO> getBonusApplications(UserDTO supervisor);

    List<DelegationApplicationDTO> getDelegationApplications(UserDTO supervisor);

}
