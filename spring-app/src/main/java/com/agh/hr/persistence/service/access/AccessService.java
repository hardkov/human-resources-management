package com.agh.hr.persistence.service.access;

import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.service.permission.UserPermissionService;
import com.agh.hr.persistence.service.subordinate.SubordinateService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccessService implements IAccessService {

    private final UserPermissionService userPermissionService;

    private final SubordinateService subordinateService;

    public AccessService(UserPermissionService userPermissionService,
                         SubordinateService subordinateService) {
        this.userPermissionService = userPermissionService;
        this.subordinateService = subordinateService;
    }

    // todo add a check for admin, probably a better place do it would be in userPermissionService

    @Override
    public boolean canRead(Long byUserId, Long userId) {
        return readableUsers(byUserId).stream()
                .map(UserDTO::getId)
                .collect(Collectors.toSet())
                .contains(userId);
    }

    @Override
    public boolean canModify(Long byUserId, Long userId) {
        return modifiableUsers(byUserId).stream()
                .map(UserDTO::getId)
                .collect(Collectors.toSet())
                .contains(userId);
    }

    @Override
    public List<UserDTO> readableUsers(Long byUserId) {
        val readableViaPermissions = userPermissionService.getReadableUsers(byUserId);
        val readableViaHierarchy = subordinateService.getSubordinates(byUserId);

        return union(readableViaPermissions, readableViaHierarchy);
    }

    @Override
    public List<UserDTO> modifiableUsers(Long byUserId) {
        val readableViaPermissions = userPermissionService.getReadableUsers(byUserId);
        val readableViaHierarchy = subordinateService.getSubordinates(byUserId);

        return union(readableViaPermissions, readableViaHierarchy);
    }

    private List<UserDTO> union(List<UserDTO> list1, List<UserDTO> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .collect(Collectors.toList());
    }

}
