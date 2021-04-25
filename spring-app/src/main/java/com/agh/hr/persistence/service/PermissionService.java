package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Permission;
import com.agh.hr.persistence.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PermissionService {


    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository){
        this.permissionRepository=permissionRepository;
    }

    public Optional<Permission> savePermission(Permission user) {
        try {
            return Optional.of(permissionRepository.save(user));
        }catch(Exception e){return Optional.empty();}

    }

    public Optional<Permission> getById(Long id) {
        return permissionRepository.findById(id);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public void deletePermission(Long permissionId) {
            permissionRepository.deleteById(permissionId);
    }

    public List<Permission> getByByAddEquals(boolean add) {
        return permissionRepository.findByAddEquals(add);
    }


}
