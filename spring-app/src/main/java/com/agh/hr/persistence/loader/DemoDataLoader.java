package com.agh.hr.persistence.loader;

import com.agh.hr.persistence.model.FooUser;
import com.agh.hr.persistence.model.Role;
import com.agh.hr.persistence.repository.FooUserRepository;
import com.agh.hr.persistence.service.RoleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("dev")
public class DemoDataLoader {

    private final FooUserRepository fooUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public DemoDataLoader(FooUserRepository fooUserRepository,
                          PasswordEncoder passwordEncoder,
                          RoleService roleService) {
        this.fooUserRepository = fooUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @EventListener
    @Order(2)
    public void appReady(ApplicationReadyEvent event) {
        val user = FooUser.builder()
                .username("wojtyczekPatryk@gmail.com")
                .password(passwordEncoder.encode("superpassword"))
                .enabled(true)
                .authorities(Collections.singleton(roleService.userRole()))
                .build();

        fooUserRepository.save(
            user
        );
    }
}
