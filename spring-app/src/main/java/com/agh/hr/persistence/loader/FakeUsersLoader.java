package com.agh.hr.persistence.loader;

import com.agh.hr.persistence.model.*;
import com.agh.hr.persistence.repository.LeaveRepository;
import com.agh.hr.persistence.repository.PermissionRepository;
import com.agh.hr.persistence.repository.UserRepository;
import com.agh.hr.persistence.service.RoleService;
import com.github.javafaker.Faker;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
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
        val employeesNumber = 20;
        val supervisorsNumber = 5;

        val minLeaves = 0;
        val maxLeaves = 4;

        val minLeaveDuration = 1;
        val maxLeaveDuration = 14;


        // supervisor
        val supervisor = fakeUser(faker, null, roleService.supervisorRole())
                .position("Supervisor")
                .username("supervisor@gmail.com")
                .build();

        val fakeSupervisors =
                Stream
                        .generate(() -> fakeUser(faker, supervisor, roleService.supervisorRole())
                                .position(faker.job().seniority() + " Project Manager")
                                .build()).limit(supervisorsNumber)
                        .collect(Collectors.toList());

        val insertedSupervisors = userRepository.saveAll(
                Stream.concat(Stream.of(supervisor), fakeSupervisors.stream()).collect(Collectors.toList())
        );

        //// USERS
        val fakeEmployees =
                Stream
                        .generate(() ->
                        fakeUser(faker, supervisor, roleService.employeeRole())
                                .build()
                        ).limit(employeesNumber)
                        .collect(Collectors.toList());

        val employee = fakeUser(faker, supervisor, roleService.employeeRole())
                .position("Ordinary User")
                .username("employee@gmail.com")
                .build();

        val insertedEmployees = userRepository.saveAll(
                Stream.concat(Stream.of(employee), fakeEmployees.stream()).collect(Collectors.toList())
        );

        val admin = fakeUser(faker, null, roleService.adminRole())
                .position("Admin")
                .username("admin@gmail.com")
                .build();

        val insertedAdmin = userRepository.save(admin);

        val insertedUsers = Stream.of(
                Stream.of(insertedAdmin), insertedEmployees.stream(), insertedSupervisors.stream()
            ).reduce(Stream::concat).get().collect(Collectors.toList());

        //// LEAVES
        val fakeLeaves = generateLeavesForUsers(
                faker,
                insertedUsers,
                minLeaves,
                maxLeaves,
                minLeaveDuration,
                maxLeaveDuration
        ).collect(Collectors.toList());

        leaveRepository.saveAll(fakeLeaves);
    }

    private PersonalData fakePersonalData(Faker faker) {
        val fakeName = faker.name();
        val minimumBirthdate = new Date(1969 - 1900, Calendar.JANUARY, 1);
        val maximumBirthdate = new Date(2000 - 1900, Calendar.MARCH, 31);
        return PersonalData.builder()
                .firstname(fakeName.firstName())
                .lastname(fakeName.lastName())
                .address(faker.address().fullAddress())
                .email(faker.internet().emailAddress())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .birthdate(faker.date()
                        .between(minimumBirthdate, maximumBirthdate)
                        .toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
    }

    private User.UserBuilder fakeUser(Faker faker, User supervisor, Role role) {
        val userPersonalData = fakePersonalData(faker);

        return User.builder()
                .username(faker.internet().emailAddress())
                .passwordHash(passwordEncoder.encode("passw0rd"))
                .supervisor(supervisor)
                .enabled(true)
                .authorities(Collections.singleton(role))
                .personalData(userPersonalData)
                .permissions(Permission.builder().add(false).build())
                .position(faker.programmingLanguage().name() + " Developer")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList());
    }

    private Leave.LeaveBuilder fakeLeave(Faker faker) {
        val minimumStartDate = new Date(2019, Calendar.JANUARY, 1);
        val maximumStartDate = new Date(2021, Calendar.MARCH, 1);
        val duration = faker.number().numberBetween(1, 14);
        val startDate = faker.date()
                .between(minimumStartDate, maximumStartDate)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return Leave.builder()
                .paid(faker.bool().bool())
                .startDate(startDate)
                .user(null)
                .endDate(startDate.plusDays(duration));
    }

    private Stream<Leave> generateLeaveForSingleUser(Faker faker, User user, int minLeaves, int maxLeaves, int minLeaveDuration, int maxLeaveDuration) {
        val startDates = IntStream
                .rangeClosed(1, 6)
                .map(m -> m * 2 - faker.number().numberBetween(0, 1))
                .mapToObj(m -> LocalDate.of(2020, m, faker.number().numberBetween(1,28)))
                .collect(Collectors.toList());

        Collections.shuffle(startDates);
        return startDates
                .stream()
                .limit(faker.number().numberBetween(minLeaves,maxLeaves))
                .map(d -> fakeLeave(faker)
                        .user(user)
                        .startDate(d)
                        .endDate(d.plusDays(faker.number().numberBetween(minLeaveDuration, maxLeaveDuration)))
                        .build()
                );
    }

    private Stream<Leave> generateLeavesForUsers(Faker faker, List<User> users, int minLeaves, int maxLeaves, int minLeaveDuration, int maxLeaveDuration) {
        return users.stream()
                .flatMap(u ->
                        generateLeaveForSingleUser(
                                faker,
                                u,
                                minLeaves,
                                maxLeaves,
                                minLeaveDuration,
                                maxLeaveDuration
                        )
                );
    }
}
