package com.agh.hr.persistence.service.permission;

import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionService implements IUserPermissionService {

    private final PermissionService permissionService;

    private final UserService userService;

    public UserPermissionService(PermissionService permissionService,
                                 UserService userService) {
        this.permissionService = permissionService;
        this.userService = userService;
    }

    @Override
    public List<UserDTO> getReadableUsers(Long byUserId) {
        if (this.userService.isAdmin(byUserId)) {
            this.userService.getAllUsers();
        }

        val readableUsersIds =
                permissionService.getUsersPermissions(byUserId).getRead();

        return getUserDTOs(readableUsersIds);
    }

    @Override
    public List<UserDTO> getModifiableUsers(Long byUserId) {
        if (this.userService.isAdmin(byUserId)) {
            this.userService.getAllUsers();
        }

        val modifiableUsersIds =
                permissionService.getUsersPermissions(byUserId).getWrite();

        return getUserDTOs(modifiableUsersIds);
    }

    private List<UserDTO> getUserDTOs(List<Long> userIds) {
        return this.userService.getUsersById(userIds);
    }
}


