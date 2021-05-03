package com.agh.hr;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.agh.hr.controllers.UserController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.PersonalDataDTO;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

public class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private Converters converters;

    @InjectMocks
    private UserController userController;

    private User userTest;
    private UserDTO userTestDTO;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);

        userTest = new UserTest();
        PersonalData personalData= new PersonalDataTest();
        personalData.setId(10L);
        personalData.setFirstname("Susan");
        personalData.setLastname("Burrow");
        userTest.setPersonalData(personalData);

        userTestDTO=new UserDTO();
        PersonalDataDTO personalDataDTO=new PersonalDataDTO();
        personalDataDTO.setFirstname("Susan");
        personalDataDTO.setLastname("Burrow");
        userTestDTO.setPersonalData(personalDataDTO);
    }

    static class UserTest extends User{}
    static class PersonalDataTest extends PersonalData{}

    @Test
    void testSuccessCreateUser() {
        when(userService.saveUser(userTest)).thenReturn(Optional.of(userTest));
        when(converters.DTOToUser(userTestDTO)).thenReturn(userTest);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertEquals(userController.insertUser(userTestDTO).getStatusCodeValue(),200);
        assertNotNull(userController.insertUser(userTestDTO).getBody());
        assertEquals(userController.insertUser(userTestDTO).getBody(),userTestDTO);
    }

    @Test
    void testFailCreateUser() {
        when(userService.saveUser(userTest)).thenReturn(Optional.empty());
        when(converters.DTOToUser(userTestDTO)).thenReturn(userTest);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertEquals(userController.insertUser(userTestDTO).getStatusCodeValue(),400);
        assertNull(userController.insertUser(userTestDTO).getBody());
    }

    @Test
    void testSuccessGetAllUsers() {
        List<User> userList=new ArrayList<>();
        for(int i=0;i<10;i++)
            userList.add(userTest);

        List<UserDTO> userDTOList=new ArrayList<>();
        for(int i=0;i<10;i++)
            userDTOList.add(userTestDTO);


        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        when(userService.getAllUsers()).thenReturn(userList);

        assertEquals(userController.getAllUsers().getStatusCodeValue(),200);
        assertNotNull(userController.getAllUsers().getBody());
        assertTrue(userController.getAllUsers().getBody().containsAll(userDTOList));
    }

    @Test
    void testSuccessGetUserByFirstname() {
        List<User> userList=new ArrayList<>();
        userList.add(userTest);

        when(userService.getByFirstname("Susan")).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertEquals(userController.getUserByFirstname("Susan").getStatusCodeValue(),200);
        assertNotNull(userController.getUserByFirstname("Susan").getBody());
        assertFalse(Objects.requireNonNull(userController.getUserByFirstname("Susan").getBody()).isEmpty());
        assertEquals(Objects.requireNonNull(userController.getUserByFirstname("Susan").getBody()).get(0).
                getPersonalData().getFirstname(),"Susan");

    }
    @Test
    void testFailGetUserByFirstname() {
        List<User> emptyUserList=new ArrayList<>();
        when(userService.getByFirstname("Sussan")).thenReturn(emptyUserList);
        assertNotNull(userController.getUserByFirstname("Sussan"));
        assertTrue(Objects.requireNonNull(userController.getUserByFirstname("Sussan").getBody()).isEmpty());

    }



}
