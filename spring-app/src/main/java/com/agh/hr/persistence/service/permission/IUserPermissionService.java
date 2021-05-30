package com.agh.hr.persistence.service.permission;

import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;

public interface IUserPermissionService {

    /**
     * Returns a list of users that user with byUserId can access via permissions.
     */
    List<UserDTO> getReadableUsers(Long byUserId);

    /**
     * Returns a list of users that user with byUserId can modify via permissions.
     */
    List<UserDTO> getModifiableUsers(Long byUserId);
}
