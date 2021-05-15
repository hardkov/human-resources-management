package com.agh.hr.persistence.service;

import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.PermissionDTO;
import com.agh.hr.persistence.model.Permission;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.PermissionRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public PermissionService(PermissionRepository permissionRepository, RoleService roleService, Converters converters){
        this.permissionRepository=permissionRepository;
        this.roleService=roleService;
        this.converters = converters;
    }

    public Optional<PermissionDTO> savePermission(PermissionDTO permissionDTO) {
        val userAuth=Auth.getCurrentUser();
        Optional<Permission> permission=getRawById(permissionDTO.getId());
        if(!permission.isPresent())
            return Optional.empty();

        if(!roleService.isAdmin(userAuth)) {
            if (!(Auth.getWriteIds(userAuth).contains(permissionDTO.getUser().getId())))
                return Optional.empty();
            if (!Auth.arePermissionsOwnedBy(permission.get(), userAuth))
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
        if(roleService.isAdmin(userAuth))
            return permissionRepository.findByIdAdmin(id);
        return permissionRepository.findById(id,Auth.getReadIds(userAuth));
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
        if(roleService.isAdmin(userAuth))
            return permissionRepository.findByUserIdAdmin(id).map(converters::permissionToDTO);
        return permissionRepository.findByUserId(id,Auth.getReadIds(userAuth)).map(converters::permissionToDTO);
    }

    public List<PermissionDTO> getAll() {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth))
            return permissionRepository.findAllAdmin().stream()
                    .map(converters::permissionToDTO)
                    .collect(Collectors.toList());
        return permissionRepository.findAll(Auth.getReadIds(userAuth)).stream()
                .map(converters::permissionToDTO)
                .collect(Collectors.toList());
    }

}
