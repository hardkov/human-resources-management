package com.agh.hr.persistence.loader;

import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.Role;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.LeaveRepository;
import com.agh.hr.persistence.repository.UserRepository;
import com.agh.hr.persistence.service.RoleService;
import com.github.javafaker.Faker;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Profile("dev")
public class FakeUsersLoader {

    private final UserRepository userRepository;
    private final LeaveRepository leaveRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public FakeUsersLoader(UserRepository userRepository,
                           LeaveRepository leaveRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.leaveRepository = leaveRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @EventListener
    @Order(2)
    public void appReady(ApplicationReadyEvent event) {
        val faker = new Faker();
        val usersNumber = 20;
        val supervisorsNumber = 5;

        val minimumLeaves = 0;
        val maximumLeaves = 10;

        val fakeUsers =
                Stream.generate(() -> fakeUser(faker, roleService.userRole()))
                        .limit(usersNumber);
        val fakeSupervisors =
                Stream.generate(() -> fakeUser(faker, roleService.supervisorRole()))
                        .limit(supervisorsNumber);

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
                .personalData(adminPersonalData)
                .position("System Administrator")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList())
                .build();

        val insertedUsers = userRepository
                .saveAll(Stream
                        .of(fakeUsers, fakeSupervisors, Stream.of(admin))
                        .reduce(Stream::concat)
                        .get().collect(Collectors.toList())
                );

        val insertedLeaves = insertedUsers.stream()
                .flatMap(u -> Stream
                        .generate(() -> fakeLeave(faker, u))
                        .limit(faker.number().numberBetween(minimumLeaves, maximumLeaves))
                )
                .collect(Collectors.toList());
        leaveRepository.saveAll(insertedLeaves);
    }

    private PersonalData fakePersonalData(Faker faker) {
        val fakeName = faker.name();
        val minimumBirthdate = Date.valueOf(LocalDate.of(1969, 1, 1));
        val maximumBirthdate = Date.valueOf(LocalDate.of(2000, 12, 31));
        return PersonalData.builder()
                .firstname(fakeName.firstName())
                .lastname(fakeName.lastName())
                .address(faker.address().fullAddress())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .birthdate(faker.date()
                        .between(minimumBirthdate, maximumBirthdate)
                        .toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
    }

    private User fakeUser(Faker faker, Role role) {
        val userPersonalData = fakePersonalData(faker);

        return User.builder()
                .username(faker.internet().emailAddress())
                .passwordHash(passwordEncoder.encode("passw0rd"))
                .enabled(true)
                .authorities(Collections.singleton(role))
                .personalData(userPersonalData)
                .position(faker.programmingLanguage().name() + " Developer")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList())
                .build();
    }

    private Leave fakeLeave(Faker faker, User user) {
        val minimumStartDate = Date.valueOf(LocalDate.of(2016, 1, 1));
        val maximumStartDate = Date.valueOf(LocalDate.of(2021, 3, 1));
        val duration = faker.number().numberBetween(1, 14);
        val startDate = faker.date()
                .between(minimumStartDate, maximumStartDate)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return Leave.builder()
                .paid(faker.bool().bool())
                .startDate(startDate)
                .user(user)
                .endDate(startDate.plusDays(duration))
                .build();
    }
}
