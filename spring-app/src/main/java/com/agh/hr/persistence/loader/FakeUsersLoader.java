package com.agh.hr.persistence.loader;

import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.UserRepository;
import com.agh.hr.persistence.service.RoleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@Component
@Profile("dev")
public class FakeUsersLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public FakeUsersLoader(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @EventListener
    @Order(2)
    public void appReady(ApplicationReadyEvent event) {

        val userPersonalData = PersonalData.builder()
                .firstname("Taylor")
                .lastname("Swift")
                .address("New York")
                .email("taylor.swift@gmail.com")
                .phoneNumber("333 333 333")
                .birthdate(LocalDate.of(1989, 12, 13))
                .build();

        val user = User.builder()
                .username("user@gmail.com")
                .passwordHash(passwordEncoder.encode("passw0rd"))
                .enabled(true)
                .authorities(Collections.singleton(roleService.userRole()))
                .personalData(userPersonalData)
                .permissions(Collections.emptyList())
                .position("user-somebody?")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList())
                .build();

        val supervisorPersonalData = PersonalData.builder()
                .firstname("Ed")
                .lastname("Sheeran")
                .address("LA")
                .birthdate(LocalDate.of(1991, 2, 17))
                .email("ed.sheeran@gmail.com")
                .phoneNumber("111 111 111")
                .build();

        val supervisor = User.builder()
                .username("supervisor@gmail.com")
                .passwordHash(passwordEncoder.encode("passw0rd"))
                .enabled(true)
                .authorities(Collections.singleton(roleService.supervisorRole()))
                .personalData(supervisorPersonalData)
                .permissions(Collections.emptyList())
                .position("supervisor-somebody?")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList())
                .build();

        val adminPersonalData = PersonalData.builder()
                .firstname("John")
                .lastname("Legend")
                .address("LA")
                .birthdate(LocalDate.of(1978, 12, 28))
                .email("john.legend@gmail.com")
                .phoneNumber("111 111 111")
                .build();

        val admin = User.builder()
                .username("admin@gmail.com")
                .passwordHash(passwordEncoder.encode("passw0rd"))
                .enabled(true)
                .authorities(Collections.singleton(roleService.adminRole()))
                .permissions(Collections.emptyList())
                .personalData(adminPersonalData)
                .position("admin-somebody?")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList())
                .build();

        val fakeUsers = Arrays.asList(user, supervisor, admin);
        userRepository.saveAll(fakeUsers);
    }
}
