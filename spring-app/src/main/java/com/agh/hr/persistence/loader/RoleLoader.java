package com.agh.hr.persistence.loader;

import com.agh.hr.persistence.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
public class RoleLoader {

    private final RoleService roleService;

    @Autowired
    public RoleLoader(RoleService roleService) {
        this.roleService = roleService;
    }


    @EventListener
    @Order(1)
    public void appReady(ApplicationReadyEvent event) {
        this.roleService.saveRole(RoleService.ADMIN_ROLE_AUTHORITY);
        this.roleService.saveRole(RoleService.USER_ROLE_AUTHORITY);
    }
}
