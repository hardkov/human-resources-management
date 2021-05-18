package com.agh.hr.persistence.loader;

import com.agh.hr.persistence.model.*;
import com.agh.hr.persistence.repository.BonusApplicationRepository;
import com.agh.hr.persistence.repository.DelegationApplicationRepository;
import com.agh.hr.persistence.repository.LeaveApplicationRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@Transactional
public class FakeUsersLoader {

    private final UserRepository userRepository;
    private final BonusApplicationRepository bonusApplicationRepository;
    private final DelegationApplicationRepository delegationApplicationRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final LeaveApplicationRepository leaveApplicationRepository;

    private final Random random = new Random();
    private static final Date MINIMUM_START_DATE = new Date(2019-1900, Calendar.JANUARY, 1);
    private static final Date MAXIMUM_START_DATE = new Date(2021 - 1900, Calendar.MARCH, 1);

    @Autowired
    public FakeUsersLoader(UserRepository userRepository,
                           LeaveApplicationRepository leaveApplicationRepository,
                           BonusApplicationRepository bonusApplicationRepository,
                           DelegationApplicationRepository delegationApplicationRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.bonusApplicationRepository = bonusApplicationRepository;
        this.delegationApplicationRepository = delegationApplicationRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.leaveApplicationRepository = leaveApplicationRepository;
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

        // for each employee generate one application
        val leaveApplications = insertedEmployees.stream()
                .map(e -> fakeLeaveApplication(faker, e))
                .collect(Collectors.toList());

        val bonusApplications = insertedEmployees.stream()
                .map(e -> fakeBonusApplication(faker, e))
                .collect(Collectors.toList());

        val delegationApplications = insertedEmployees.stream()
                .map(e -> fakeDelegationApplication(faker, e))
                .collect(Collectors.toList());

        this.leaveApplicationRepository.saveAll(leaveApplications);
        this.bonusApplicationRepository.saveAll(bonusApplications);
        this.delegationApplicationRepository.saveAll(delegationApplications);


        val admin = fakeUser(faker, null, roleService.adminRole())
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
                .contracts(Stream.of(fakeContract(faker).build()).collect(Collectors.toList()))
                .leaves(generateLeaveForSingleUser(faker).collect(Collectors.toList()))
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList());
    }

    private LocalDate fakeDate(Faker faker, Date from, Date to){
        return fakeTime(faker, from, to)
                .toLocalDate();
    }

    private LocalDateTime fakeDateTime(Faker faker, Date from, Date to) {
        return fakeTime(faker, from, to)
                .toLocalDateTime();

    }

    private ZonedDateTime fakeTime(Faker faker, Date from, Date to) {
        return faker.date()
                .between(from, to)
                .toInstant()
                .atZone(ZoneId.systemDefault());
    }

    private LocalDateTime fakeTimeAfter(Faker faker, LocalDateTime dateTime) {
        val after = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        val afterDate = Date.from(after);
        return faker.date()
                .future(21, TimeUnit.DAYS, afterDate)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private LeaveApplication fakeLeaveApplication(Faker faker, User user) {
        val fakeStartDate = fakeDateTime(faker, MINIMUM_START_DATE, MINIMUM_START_DATE);
        val fakeEndDate = fakeTimeAfter(faker, fakeStartDate);

        val paid = Math.random() < 0.5;

        return LeaveApplication.builder()
                .startDate(fakeStartDate)
                .endDate(fakeEndDate)
                .content(faker.lorem().paragraph(4))
                .status(randomStatus())
                .user(user)
                .place(faker.address().cityName())
                .paid(paid)
                .build();
    }

    private DelegationApplication fakeDelegationApplication(Faker faker, User user) {
        val fakeStartDate = fakeDateTime(faker, MINIMUM_START_DATE, MAXIMUM_START_DATE);
        val fakeEndDate = fakeTimeAfter(faker, fakeStartDate);

        return DelegationApplication.builder()
                .startDate(fakeStartDate)
                .endDate(fakeEndDate)
                .content(faker.lorem().paragraph(4))
                .status(randomStatus())
                .user(user)
                .place(faker.address().cityName())
                .destination(faker.address().country())
                .build();
    }

    private BonusApplication fakeBonusApplication(Faker faker, User user) {
        return BonusApplication.builder()
                .content(faker.lorem().paragraph(4))
                .status(randomStatus())
                .user(user)
                .place(faker.address().cityName())
                .money(new BigDecimal(faker.number().numberBetween(10, 50)*100))
                .build();
    }

    private Status randomStatus() {
        val possibleValues = Status.values();
        val selected = random.nextInt(possibleValues.length);

        return possibleValues[selected];
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
