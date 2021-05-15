package com.agh.hr.persistence.service.subordinate;

import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;

public interface ISubordinateService {

    boolean isSubordinate(UserDTO supervisor, UserDTO subordinate);

    List<UserDTO> getSubordinates(UserDTO supervisor);

}
