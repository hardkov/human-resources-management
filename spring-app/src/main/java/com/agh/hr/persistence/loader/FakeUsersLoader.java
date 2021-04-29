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

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

        val minLeaves = 0;
        val maxLeaves = 4;

        val minLeaveDuration = 1;
        val maxLeaveDuration = 14;

        //// USERS
        val fakeUsers =
                Stream
                        .generate(() ->
                        fakeUser(faker, roleService.userRole())
                                .build()
                        ).limit(usersNumber);

        val fakeSupervisors =
                Stream
                        .generate(() ->
                        fakeUser(faker, roleService.supervisorRole())
                                .position(faker.job().seniority() + " Project Manager")
                                .build()
                        ).limit(supervisorsNumber);

        val admin = fakeUser(faker, roleService.adminRole())
                .position("System Administrator")
                .username("admin@gmail.com")
                .build();

        val allUsers = Stream
                .of(fakeUsers, fakeSupervisors, Stream.of(admin))
                .reduce(Stream::concat)
                .get().collect(Collectors.toList());

        val insertedUsers = userRepository.saveAll(allUsers);

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
        val minimumBirthdate = new Date(1969, Calendar.JANUARY, 1);
        val maximumBirthdate = new Date(2000, Calendar.MARCH, 31);
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

    private User.UserBuilder fakeUser(Faker faker, Role role) {
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
                .applications(Collections.emptyList());
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
