package com.agh.hr;
import com.agh.hr.persistence.model.Permission;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.UserRepository;
import com.agh.hr.persistence.service.RoleService;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceDatabaseIntegrationTests {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    private User userTest;

    @Autowired
    public UserServiceDatabaseIntegrationTests(UserService userService,
                                               PasswordEncoder passwordEncoder,
                                               RoleService roleService){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.roleService=roleService;

    }

    @BeforeEach
    public void setup(){
        val personalData= PersonalData.builder()
                .firstname("Susan")
                .lastname("Barrow")
                .build();

        this.userTest = User.builder()
                .id(10L)
                .username("sus")
                .passwordHash(passwordEncoder.encode("passw0rd"))
                .enabled(true)
                .authorities(Collections.singleton(roleService.employeeRole()))
                .personalData(personalData)
                .position("Tester")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList()).build();

        val permissions= Permission.builder().build();
        permissions.addToWrite(10L);
        permissions.setAdd(true);
        userTest.setPermissions(permissions);
        userTest.getPersonalData().setUser(userTest);
    }

    @Test
    void testSaveUser(){
        Optional<User> result=userService.saveUser(userTest,userTest,true);
        assertTrue(result.isPresent());
    }

    @Test
    void testSuccessFindByUsername(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,userTest,true).isPresent()),
                ()->assertTrue(userService.findByUsername("sus").isPresent())
        );
    }

    @Test
    void testFailFindByUsername(){
      assertFalse(userService.findByUsername("sus").isPresent());
    }

    @Test
    void testGetById(){
        Optional<User> result=userService.saveUser(userTest,userTest,true);
        assertTrue(userService.getById(userTest.getId(),userTest).isPresent());

    }

    @Test
    void testSuccessGetByFirstname(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,userTest,true).isPresent()),
                ()->assertEquals(1,userService.getByFirstname("Susan",userTest).size())
        );

    }

    @Test
    void testFailGetByFirstname(){
        assertEquals(0,userService.getByFirstname("Susan",userTest).size());
    }

    @Test
    void testSuccessGetByLastname(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,userTest,true).isPresent()),
                ()->assertEquals(1,userService.getByLastname("Barrow",userTest).size())
        );

    }

    @Test
    void testFailGetByLastname(){
        assertEquals(0,userService.getByLastname("Barrow",userTest).size());
    }

    @Test
    void testSuccessGetByFullName(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,userTest,true).isPresent()),
                ()->assertEquals(1,userService.getByFullName("Susan","Barrow",userTest).size())
        );
    }

    @Test
    void testFailGetByFullName(){
        assertEquals(0,userService
                .getByFullName("Susan","Barrow",userTest).size());
    }

    @Test
    void testDeleteUser(){

        assertAll(
                ()->userService.saveUser(userTest,userTest,true),
                ()->userService.deleteUser(userTest.getId(),userTest),
                ()->assertFalse(userService.getById(userTest.getId(),userTest).isPresent())
        );
    }

}
