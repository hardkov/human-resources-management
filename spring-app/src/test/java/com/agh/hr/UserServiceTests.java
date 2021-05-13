package com.agh.hr;

import com.agh.hr.controllers.UserController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.UserRepository;
import com.agh.hr.persistence.service.RoleService;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    private UserRepository userRepository;

    private UserService userService;
    private User user;

    private String username ;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        RoleService roleService = mock(RoleService.class);
        userService = new UserService(userRepository, roleService);

        this.username = "foo@gmail.com";
        this.user = User.builder()
                .id(10L)
                .username(username)
                .build();
    }

    @Test
    public void getUsernameSuccess(){
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        val result = userService.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    public void getUsernameFail(){
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        val actual = userService.findByUsername(username);
        val expected = Optional.empty();
        assertEquals(expected, actual);
    }

}
