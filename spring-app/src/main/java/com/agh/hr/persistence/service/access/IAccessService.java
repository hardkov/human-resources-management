package com.agh.hr.persistence.service.access;

import com.agh.hr.persistence.dto.UserDTO;

import java.util.List;

public interface IAccessService {

    /**
     * Whether user with byUserId can "read" user with id.
     */
    boolean canRead(Long byUserId, Long userId);

    /**
     * Whether user with byUserId can "modify" user with id.
     */
    boolean canModify(Long byUserId, Long userId);


    /**
     * Returns a list of all users that supplied user can "read":
     * Union of:
     *  - subordinates
     *  - additional users with granted read permission
     */
    List<UserDTO> readableUsers(Long byUserId);

    /**
     * Returns a list of all users that supplied user can "write":
     * Union of:
     *  - subordinates
     *  - additional users with granted read permission
     */
    List<UserDTO> modifiableUsers(Long byUserId);

}
