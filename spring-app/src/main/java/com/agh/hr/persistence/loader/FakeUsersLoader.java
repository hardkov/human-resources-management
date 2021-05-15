package com.agh.hr.persistence.loader;

import com.agh.hr.persistence.model.*;
import com.agh.hr.persistence.repository.LeaveRepository;
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

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
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
        val faker = new Faker();
        val employeesNumber = 20;
        val supervisorsNumber = 5;

        //// USERS
        val fakeEmployees =
                Stream
                        .generate(() ->
                        fakeUser(faker, roleService.employeeRole())
                                .build()
                        ).limit(employeesNumber)
                        .collect(Collectors.toList());

        val employee = fakeUser(faker, roleService.employeeRole())
                .position("Ordinary User")
                .username("employee@gmail.com")
                .build();

        val insertedEmployees = userRepository.saveAll(
                Stream.concat(Stream.of(employee), fakeEmployees.stream()).collect(Collectors.toList())
        );

        val fakeSupervisors =
                Stream
                        .generate(() ->{
                            List<Long> shuffledUsers = insertedEmployees
                                    .stream()
                                    .map(User::getId)
                                    .collect(Collectors.toList());
                            Collections.shuffle(shuffledUsers);
                            List<Long> supervisedUsers = shuffledUsers.subList(0, employeesNumber/4);
                            return fakeUser(faker, roleService.supervisorRole())
                                .position(faker.job().seniority() + " Project Manager")
                                .permissions(Permission.builder()
                                        .add(true)
                                        .read(supervisedUsers)
                                        .write(supervisedUsers)
                                        .build())
                                .build();
                        }).limit(supervisorsNumber)
                        .collect(Collectors.toList());

        List<Long> shuffledUsers = insertedEmployees
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        Collections.shuffle(shuffledUsers);
        List<Long> supervisedUsers = shuffledUsers.subList(0, employeesNumber/4);
        val supervisor = fakeUser(faker, roleService.supervisorRole())
                .position("Supervisor")
                .username("supervisor@gmail.com")
                .permissions(Permission.builder()
                        .add(true)
                        .read(supervisedUsers)
                        .write(supervisedUsers)
                        .build())
                .build();

        val insertedSupervisors = userRepository.saveAll(
                Stream.concat(Stream.of(supervisor), fakeSupervisors.stream()).collect(Collectors.toList())
        );

        val admin = fakeUser(faker, roleService.adminRole())
                .position("Admin")
                .username("admin@gmail.com")
                .build();

        val insertedAdmin = userRepository.save(admin);
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
                .birthdate(fakeDate(faker, minimumBirthdate, maximumBirthdate))
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
                .permissions(Permission.builder().add(false).build())
                .position(faker.programmingLanguage().name() + " Developer")
                .contracts(Stream.of(fakeContract(faker).build()).collect(Collectors.toList()))
                .leaves(generateLeaveForSingleUser(faker).collect(Collectors.toList()))
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList());
    }

    private LocalDate fakeDate(Faker faker, Date from, Date to){
        return faker.date()
                .between(from, to)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private Leave.LeaveBuilder fakeLeave(Faker faker) {
        val minimumStartDate = new Date(2019-1900, Calendar.JANUARY, 1);
        val maximumStartDate = new Date(2021-1900, Calendar.MARCH, 1);
        val duration = faker.number().numberBetween(1, 14);
        val startDate = fakeDate(faker, minimumStartDate, maximumStartDate);
        return Leave.builder()
                .paid(faker.bool().bool())
                .startDate(startDate)
                .user(null)
                .endDate(startDate.plusDays(duration));
    }

    private Contract.ContractBuilder fakeContract(Faker faker) {
        val minimumStartDate = new Date(2019-1900, Calendar.JANUARY, 1);
        val maximumStartDate = new Date(2020-1900, Calendar.MARCH, 1);

        val minimumEndDate = new Date(2021-1900, Calendar.JANUARY, 1);
        val maximumEndDate = new Date(2023-1900, Calendar.MARCH, 1);

        val minimumSalaryHundreds = 25;
        val maximumSalaryHundreds = 200;

        return Contract.builder()
                .startDate(fakeDate(faker, minimumStartDate, maximumStartDate))
                .endDate(fakeDate(faker, minimumEndDate, maximumEndDate))
                .baseSalary(new BigDecimal(faker.number().numberBetween(minimumSalaryHundreds, maximumSalaryHundreds)*100))
                .contractType(ContractType.values()[faker.number().numberBetween(0, ContractType.values().length)]);
    }

    private Stream<Leave> generateLeaveForSingleUser(Faker faker) {
        val minLeaves = 0;
        val maxLeaves = 4;
        val minLeaveDuration = 1;
        val maxLeaveDuration = 14;

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
                        .startDate(d)
                        .endDate(d.plusDays(faker.number().numberBetween(minLeaveDuration, maxLeaveDuration)))
                        .build()
                );
    }
}
