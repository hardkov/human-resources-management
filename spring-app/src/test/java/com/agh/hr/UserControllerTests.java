package com.agh.hr;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.agh.hr.controllers.UserController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.PersonalDataDTO;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.model.Permission;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
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
    private Principal principal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        val personalData= PersonalData.builder()
                .firstname("Susan")
                .lastname("Barrow")
                .build();

        this.userTest = User.builder()
                .id(10L)
                .personalData(personalData)
                .build();

        val personalDataDTO= PersonalDataDTO.builder()
                .firstname("Susan")
                .lastname("Barrow")
                .build();

        this.userTestDTO = UserDTO.builder()
                .id(10L)
                .personalData(personalDataDTO)
                .build();
    }

    @Test
    void testSuccessCreateUser() {
        when(userService.saveUser(userTestDTO)).thenReturn(Optional.of(userTestDTO));
        when(converters.DTOToUser(userTestDTO)).thenReturn(userTest);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertAll(
                ()->assertEquals(200,userController.insertUser(userTestDTO).getStatusCodeValue()),
                ()->assertNotNull(userController.insertUser(userTestDTO).getBody()),
                ()->assertEquals(userController.insertUser(userTestDTO).getBody(),userTestDTO)
        );


    }

    @Test
    void testFailCreateUser() {
        when(userService.saveUser(userTestDTO)).thenReturn(Optional.empty());
        when(converters.DTOToUser(userTestDTO)).thenReturn(userTest);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertAll(
                ()->assertEquals(400,userController.insertUser(userTestDTO).getStatusCodeValue()),
                ()->assertNull(userController.insertUser(userTestDTO).getBody())
        );

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
        when(userService.getAllUsers()).thenReturn(userDTOList);
        assertAll(
                ()->assertEquals(200,userController.getAllUsers().getStatusCodeValue()),
                ()->assertNotNull(userController.getAllUsers().getBody()),
                ()->assertTrue(userController.getAllUsers().getBody().containsAll(userDTOList))
        );

    }

    @Test
    void testSuccessGetUserById() {

        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        when(userService.getById(10L)).thenReturn(Optional.of(userTestDTO));
        assertAll(
                ()->assertEquals(200,userController.getUserById(10L).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserById(10L).getBody()),
                ()->assertEquals(10L,Objects.requireNonNull(userController.getUserById(10L)
                        .getBody()).getId().longValue())
        );

    }

    @Test
    void testFailGetUserById() {

        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        when(userService.getById(10L)).thenReturn(Optional.of(userTestDTO));
        assertAll(
                ()->assertEquals(404,userController.getUserById(20L).getStatusCodeValue()),
                ()->assertNull(userController.getUserById(20L).getBody())
        );

    }

    @Test
    void testSuccessGetUserByFirstname() {
        List<UserDTO> userList=new ArrayList<>();
        userList.add(userTestDTO);

        when(userService.getByFirstname("Susan")).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByFirstname("Susan").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFirstname("Susan").getBody()),
                ()->assertFalse(Objects.requireNonNull(userController.getUserByFirstname("Susan").getBody()).isEmpty()),
                ()->assertEquals("Susan",Objects.requireNonNull(userController.getUserByFirstname("Susan")
                        .getBody()).get(0).getPersonalData().getFirstname())
        );

    }
    @Test
    void testFailGetUserByFirstname() {
        List<UserDTO> emptyUserList=new ArrayList<>();
        when(userService.getByFirstname("Sussan")).thenReturn(emptyUserList);
        assertAll(
                ()->assertNotNull(userController.getUserByFirstname("Sussan")),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByFirstname("Sussan").getBody()).isEmpty())
        );

    }

    @Test
    void testSuccessGetUserByLastname() {
        List<UserDTO> userList=new ArrayList<>();
        userList.add(userTestDTO);

        when(userService.getByLastname("Barrow")).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByLastname("Barrow").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByLastname("Barrow").getBody()),
                ()->assertFalse(Objects.requireNonNull(userController.getUserByLastname("Barrow").getBody()).isEmpty()),
                ()->assertEquals("Barrow",Objects.requireNonNull(userController.getUserByLastname("Barrow")
                        .getBody()).get(0).getPersonalData().getLastname())
        );


    }

    @Test
    void testFailGetUserByLastname() {
        List<UserDTO> emptyUserList=new ArrayList<>();
        when(userService.getByLastname("Sussan")).thenReturn(emptyUserList);
        assertAll(
                ()->assertEquals(200,userController.getUserByLastname("Sussan").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByLastname("Sussan")),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByLastname("Sussan").getBody()).isEmpty())
        );


    }

    @Test
    void testSuccessGetUserByFullname() {
        List<UserDTO> userList=new ArrayList<>();
        userList.add(userTestDTO);
        when(userService.getByFullName("Susan","Barrow")).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByFullName("Susan","Barrow").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFullName("Susan","Barrow")),
                ()->assertEquals("Susan",Objects.requireNonNull(userController
                        .getUserByFullName("Susan","Barrow").getBody())
                        .get(0).getPersonalData().getFirstname()),
                ()->assertEquals("Barrow",Objects.requireNonNull(userController
                        .getUserByFullName("Susan","Barrow").getBody()).get(0).getPersonalData().getLastname())
        );

    }

    @Test
    void testFailGetUserByFullname() {
        List<UserDTO> emptyUserList=new ArrayList<>();
        when(userService.getByFullName("Susan","Barrow")).thenReturn(emptyUserList);
        assertAll(
                ()->assertEquals(200,userController.getUserByFullName("Susan","Barrow").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFullName("Susan","Barrow")),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByFullName("Susan","Barrow").getBody()).isEmpty())
        );

    }

    @Test
    void testSuccessUpdateUser() {

        when(userService.updateUser(userTestDTO)).thenReturn(Optional.of(userTestDTO));
        when(userService.getById(10L)).thenReturn(Optional.of(userTestDTO));

        assertEquals(202,userController.updateUser(userTestDTO).getStatusCodeValue());


    }

    @Test
    void testFailUpdateUser() {

        when(userService.updateUser(userTestDTO)).thenReturn(Optional.empty());

        assertEquals(400,userController.updateUser(userTestDTO).getStatusCodeValue());


    }
}
