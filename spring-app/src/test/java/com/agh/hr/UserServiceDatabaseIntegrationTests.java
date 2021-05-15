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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;
import java.util.Collection;
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
    private User userAuth;

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
        userTest.getPersonalData().setUser(userTest);

        this.userAuth = User.builder()
                .username("test")
                .passwordHash(passwordEncoder.encode("passw0rd"))
                .enabled(true)
                .authorities(Collections.singleton(roleService.employeeRole()))
                .position("Tester")
                .leaves(Collections.emptyList())
                .bonuses(Collections.emptyList())
                .delegations(Collections.emptyList())
                .applications(Collections.emptyList()).build();

        val permissions= Permission.builder().build();
        permissions.addToWrite(10L);
        permissions.setAdd(true);
        userAuth.setPermissions(permissions);


        Authentication auth=new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return userAuth;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void testSaveUser(){
        Optional<User> result=userService.saveUser(userTest,true);
        assertTrue(result.isPresent());
    }

    @Test
    void testSuccessFindByUsername(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,true).isPresent()),
                ()->assertTrue(userService.findByUsername("sus").isPresent())
        );
    }

    @Test
    void testFailFindByUsername(){
      assertFalse(userService.findByUsername("sus").isPresent());
    }

    @Test
    void testGetById(){
        Optional<User> result=userService.saveUser(userTest,true);
        assertAll(
                ()->assertTrue(result.isPresent()),
                ()->assertTrue(userService.getById(result.get().getId()).isPresent())
        );

    }

    @Test
    void testSuccessGetByFirstname(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,true).isPresent()),
                ()->assertEquals(1,userService.getByFirstname("Susan").size())
        );

    }

    @Test
    void testFailGetByFirstname(){
        assertEquals(0,userService.getByFirstname("Susan").size());
    }

    @Test
    void testSuccessGetByLastname(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,true).isPresent()),
                ()->assertEquals(1,userService.getByLastname("Barrow").size())
        );

    }

    @Test
    void testFailGetByLastname(){
        assertEquals(0,userService.getByLastname("Barrow").size());
    }

    @Test
    void testSuccessGetByFullName(){
        assertAll(
                ()->assertTrue(userService.saveUser(userTest,true).isPresent()),
                ()->assertEquals(1,userService.getByFullName("Susan","Barrow").size())
        );
    }

    @Test
    void testFailGetByFullName(){
        assertEquals(0,userService
                .getByFullName("Susan","Barrow").size());
    }

    @Test
    void testDeleteUser(){
        Optional<User> result=userService.saveUser(userTest,true);
        assertAll(
                ()->assertTrue(result.isPresent()),
                ()->userService.deleteUser(result.get().getId()),
                ()->assertFalse(userService.getById(userTest.getId()).isPresent())
        );
    }

}
