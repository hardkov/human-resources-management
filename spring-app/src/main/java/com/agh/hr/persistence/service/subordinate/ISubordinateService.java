package com.agh.hr.persistence.service.subordinate;

import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;

public interface ISubordinateService {

    boolean isSubordinate(Long supervisorId, Long subordinateId);

    List<UserDTO> getSubordinates(Long supervisorId);

}
