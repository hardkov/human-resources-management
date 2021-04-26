package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.Role;
import com.agh.hr.persistence.repository.RoleRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoleService {

    public static final String USER_AUTHORITY = "USER";
    public static final String SUPERVISOR_AUTHORITY = "SUPERVISOR";
    public static final String ADMIN_AUTHORITY = "ADMIN";

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveAuthority(String authority) {
        val role = Role.builder().authority(authority).build();
        return this.roleRepository.save(role);
    }

    public Role userRole() {
        return this.roleRepository.findByAuthority(USER_AUTHORITY)
                .orElseThrow(() -> new NoSuchElementException(USER_AUTHORITY));
    }

    public Role supervisorRole() {
        return this.roleRepository.findByAuthority(SUPERVISOR_AUTHORITY)
                .orElseThrow(() -> new NoSuchElementException(SUPERVISOR_AUTHORITY));
    }

    public Role adminRole() {
        return this.roleRepository.findByAuthority(ADMIN_AUTHORITY)
                .orElseThrow(() -> new NoSuchElementException(ADMIN_AUTHORITY));
    }

    public static List<String> authorities() {
        return Arrays.asList(USER_AUTHORITY, SUPERVISOR_AUTHORITY, ADMIN_AUTHORITY);
    }
}
