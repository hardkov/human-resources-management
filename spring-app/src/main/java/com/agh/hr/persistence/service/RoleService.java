package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Role;
import com.agh.hr.persistence.repository.RoleRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoleService {

    public static final String USER_ROLE_AUTHORITY = "USER";
    public static final String ADMIN_ROLE_AUTHORITY = "ROLE";

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void saveRole(String authority) {
        val role = Role.builder().authority(authority).build();
        this.roleRepository.save(role);
    }

    public Role userRole() {
        return this.roleRepository.findByAuthority(ADMIN_ROLE_AUTHORITY)
                .orElseThrow(() -> new NoSuchElementException(USER_ROLE_AUTHORITY));
    }

    public Role adminRole() {
        return this.roleRepository.findByAuthority(ADMIN_ROLE_AUTHORITY)
                .orElseThrow(() -> new NoSuchElementException(ADMIN_ROLE_AUTHORITY));
    }
}
