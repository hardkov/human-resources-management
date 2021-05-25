package com.agh.hr.persistence.service.permission;

import com.agh.hr.model.error.NotFoundException;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.PermissionDTO;
import com.agh.hr.persistence.model.Permission;
import com.agh.hr.persistence.repository.PermissionRepository;
import com.agh.hr.persistence.service.RoleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionService {


    private final PermissionRepository permissionRepository;
    private final RoleService roleService;
    private final Converters converters;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository,
                             RoleService roleService,
                             Converters converters){
        this.permissionRepository=permissionRepository;
        this.roleService=roleService;
        this.converters = converters;
    }

    public PermissionDTO getUsersPermissions(Long userId) throws NotFoundException {
        return this.permissionRepository.findByUserId(userId)
                .map(converters::permissionToDTO)
                .orElseThrow(() -> new NotFoundException("Permission", userId));
    }

    public Optional<PermissionDTO> savePermission(PermissionDTO permissionDTO) {
        val userAuth= Auth.getCurrentUser();
        Optional<Permission> permission=getRawById(permissionDTO.getId());
        if(!permission.isPresent())
            return Optional.empty();

        Auth.cleanPermissions(permissionDTO);
        if(!roleService.isAdmin(userAuth)) {
            if (!(Auth.getWriteIds(userAuth).contains(permissionDTO.getUser().getId())))
                return Optional.empty();
            if (!Auth.arePermissionsOwnedBy(permissionDTO, userAuth))
                return Optional.empty();
        }

        converters.updatePermissionWithDTO(permissionDTO,permission.get());
        try {
            return Optional.of(permissionRepository.save(permission.get()))
                    .map(converters::permissionToDTO);
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Permission> getRawById(Long id) {
        val userAuth=Auth.getCurrentUser();
        return permissionRepository.findById(id,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth));
    }

    public Optional<PermissionDTO> getById(Long id) {
            return getRawById(id).map(converters::permissionToDTO);
    }

    public void deletePermission(Long permissionId) {
            permissionRepository.deleteById(permissionId);
    }

    public List<Permission> getByByAddEquals(boolean add) {
        return permissionRepository.findByAddEquals(add);
    }

    public Optional<PermissionDTO> getByUserId(Long id) {
        val userAuth=Auth.getCurrentUser();
        return permissionRepository.findByUserId(id,Auth.getReadIds(userAuth),roleService.isAdmin(userAuth))
                .map(converters::permissionToDTO);
    }

    public List<PermissionDTO> getAll() {
        val userAuth=Auth.getCurrentUser();
        return permissionRepository.findAll(Auth.getReadIds(userAuth),roleService.isAdmin(userAuth)).stream()
                .map(converters::permissionToDTO)
                .collect(Collectors.toList());
    }

}
