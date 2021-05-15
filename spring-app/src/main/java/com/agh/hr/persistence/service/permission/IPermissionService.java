package com.agh.hr.persistence.service.permission;

import com.agh.hr.persistence.dto.UserDTO;

public interface IPermissionService {

    /**
     * Whether user can read data from another user with supplied id.
     */
    boolean canRead(UserDTO user, Long id);

    /**
     * Whether user can modify another user with supplied id.
     */
    boolean canModify(UserDTO user, Long id);

}
