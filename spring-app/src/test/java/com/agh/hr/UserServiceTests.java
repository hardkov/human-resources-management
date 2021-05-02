package com.agh.hr;

import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.UserRepository;
import com.agh.hr.persistence.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//This is a snippet on how service testing could be done in a future
public class UserServiceTests{

    //Class to be tested
    private UserService userService;

    //Dependencies
    private UserRepository userRepository;


    @BeforeEach
    public void setup(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    class UserTest extends User{}
    @Test
    public void getUsernameSuccess(){
        User user = new UserTest();
        user.setUsername("susan");
        when(userRepository.findByUsername("susan")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("susan");
        assertTrue(result.isPresent());
        assertEquals("susan",result.get().getUsername());
    }

    @Test
    public void getUsernameFail(){
        when(userRepository.findByUsername("susan")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername("susan");
        assertEquals(Optional.empty(),result);
    }

}
