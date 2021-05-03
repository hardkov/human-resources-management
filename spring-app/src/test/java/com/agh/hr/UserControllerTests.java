package com.agh.hr;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        personalData.setFirstname("Susan");
        personalData.setLastname("Barrow");
        userTest.setPersonalData(personalData);
        userTest.setId(10L);

        userTestDTO=new UserDTO();
        PersonalDataDTO personalDataDTO=new PersonalDataDTO();
        personalDataDTO.setId(10L);
        personalDataDTO.setFirstname("Susan");
        personalDataDTO.setLastname("Barrow");
        userTestDTO.setPersonalData(personalDataDTO);
        userTestDTO.setId(10L);
    }

    static class UserTest extends User{}
    static class PersonalDataTest extends PersonalData{}

    @Test
    void testSuccessCreateUser() {
        when(userService.saveUser(userTest)).thenReturn(Optional.of(userTest));
        when(converters.DTOToUser(userTestDTO)).thenReturn(userTest);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertAll(
                ()->assertEquals(200,userController.insertUser(userTestDTO).getStatusCodeValue()),
                ()->assertNotNull(userController.insertUser(userTestDTO).getBody()),
                ()->assertEquals(userController.insertUser(userTestDTO).getBody(),userTestDTO)

        );

        verify(converters, times(3)).userToDTO(userTest);
        verify(converters, times(3)).DTOToUser(userTestDTO);
    }

    @Test
    void testFailCreateUser() {
        when(userService.saveUser(userTest)).thenReturn(Optional.empty());
        when(converters.DTOToUser(userTestDTO)).thenReturn(userTest);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertAll(
                ()->assertEquals(400,userController.insertUser(userTestDTO).getStatusCodeValue()),
                ()->assertNull(userController.insertUser(userTestDTO).getBody())
        );
        verify(converters, times(0)).userToDTO(userTest);
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
        assertAll(
                ()->assertEquals(200,userController.getAllUsers().getStatusCodeValue()),
                ()->assertNotNull(userController.getAllUsers().getBody()),
                ()->assertTrue(userController.getAllUsers().getBody().containsAll(userDTOList))
        );
        verify(converters, times(30)).userToDTO(userTest);
    }

    @Test
    void testSuccessGetUserById() {

        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        when(userService.getById(10L)).thenReturn(Optional.of(userTest));
        assertAll(
                ()->assertEquals(200,userController.getUserById(10L).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserById(10L).getBody()),
                ()->assertEquals(10L,Objects.requireNonNull(userController.getUserById(10L)
                        .getBody()).getId().longValue())
        );
        verify(converters, times(3)).userToDTO(userTest);
    }

    @Test
    void testFailGetUserById() {

        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        when(userService.getById(10L)).thenReturn(Optional.of(userTest));
        assertAll(
                ()->assertEquals(404,userController.getUserById(20L).getStatusCodeValue()),
                ()->assertNull(userController.getUserById(20L).getBody())
        );
        verify(converters, times(0)).userToDTO(userTest);
    }

    @Test
    void testSuccessGetUserByFirstname() {
        List<User> userList=new ArrayList<>();
        userList.add(userTest);

        when(userService.getByFirstname("Susan")).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByFirstname("Susan").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFirstname("Susan").getBody()),
                ()->assertFalse(Objects.requireNonNull(userController.getUserByFirstname("Susan").getBody()).isEmpty()),
                ()->assertEquals("Susan",Objects.requireNonNull(userController.getUserByFirstname("Susan")
                        .getBody()).get(0).getPersonalData().getFirstname())
        );
        verify(converters, times(4)).userToDTO(userTest);
    }
    @Test
    void testFailGetUserByFirstname() {
        List<User> emptyUserList=new ArrayList<>();
        when(userService.getByFirstname("Sussan")).thenReturn(emptyUserList);
        assertAll(
                ()->assertNotNull(userController.getUserByFirstname("Sussan")),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByFirstname("Sussan").getBody()).isEmpty())
        );
        verify(converters, times(0)).userToDTO(userTest);
    }

    @Test
    void testSuccessGetUserByLastname() {
        List<User> userList=new ArrayList<>();
        userList.add(userTest);

        when(userService.getByLastname("Barrow")).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByLastname("Barrow").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByLastname("Barrow").getBody()),
                ()->assertFalse(Objects.requireNonNull(userController.getUserByLastname("Barrow").getBody()).isEmpty()),
                ()->assertEquals("Barrow",Objects.requireNonNull(userController.getUserByLastname("Barrow")
                        .getBody()).get(0).getPersonalData().getLastname())
        );
        verify(converters, times(4)).userToDTO(userTest);

    }

    @Test
    void testFailGetUserByLastname() {
        List<User> emptyUserList=new ArrayList<>();
        when(userService.getByLastname("Sussan")).thenReturn(emptyUserList);
        assertAll(
                ()->assertEquals(200,userController.getUserByLastname("Sussan").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByLastname("Sussan")),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByLastname("Sussan").getBody()).isEmpty())
        );
        verify(converters, times(0)).userToDTO(userTest);

    }

    @Test
    void testSuccessGetUserByFullname() {
        List<User> userList=new ArrayList<>();
        userList.add(userTest);
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
        verify(converters, times(4)).userToDTO(userTest);
    }

    @Test
    void testFailGetUserByFullname() {
        List<User> emptyUserList=new ArrayList<>();
        when(userService.getByFullName("Susan","Barrow")).thenReturn(emptyUserList);
        assertAll(
                ()->assertEquals(200,userController.getUserByFullName("Susan","Barrow").getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFullName("Susan","Barrow")),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByFullName("Susan","Barrow").getBody()).isEmpty())
        );
        verify(converters, times(0)).userToDTO(userTest);
    }

    @Test
    void testSuccessUpdateUser() {

        when(userService.saveUser(userTest)).thenReturn(Optional.of(userTest));
        when(userService.getById(10L)).thenReturn(Optional.of(userTest));

        assertEquals(202,userController.updateUser(userTestDTO).getStatusCodeValue());
        verify(converters, times(1)).updateUserWithDTO(userTestDTO,userTest);

    }

    @Test
    void testFailUpdateUser() {

        when(userService.saveUser(userTest)).thenReturn(Optional.of(userTest));
        when(userService.getById(20L)).thenReturn(Optional.of(userTest));

        assertEquals(404,userController.updateUser(userTestDTO).getStatusCodeValue());
        verify(converters, times(0)).updateUserWithDTO(userTestDTO,userTest);

    }
}
