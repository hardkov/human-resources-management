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
import org.mockito.Mockito;
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
    public void setup() {
        MockitoAnnotations.openMocks(this);

        val personalData= PersonalData.builder()
                .firstname("Susan")
                .lastname("Barrow")
                .build();
        val permission=Permission.builder()
                .add(true).build();
        permission.addToWrite(10L);
        permission.addToRead(10L);

        this.userTest = User.builder()
                .id(10L)
                .personalData(personalData)
                .permissions(permission)
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
        when(userService.saveUser(userTest)).thenReturn(Optional.of(userTest));
        when(converters.DTOToUser(userTestDTO)).thenReturn(userTest);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);

        assertAll(
                ()->assertEquals(200,userController.insertUser(userTestDTO,userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.insertUser(userTestDTO,userTest).getBody()),
                ()->assertEquals(userController.insertUser(userTestDTO,userTest).getBody(),userTestDTO)
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
                ()->assertEquals(400,userController.insertUser(userTestDTO,userTest).getStatusCodeValue()),
                ()->assertNull(userController.insertUser(userTestDTO,userTest).getBody())
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
        when(userService.getAllUsers(userTest)).thenReturn(userList);
        assertAll(
                ()->assertEquals(200,userController.getAllUsers(userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.getAllUsers(userTest).getBody()),
                ()->assertTrue(userController.getAllUsers(userTest).getBody().containsAll(userDTOList))
        );
        verify(converters, times(30)).userToDTO(userTest);
    }

    @Test
    void testSuccessGetUserById() {

        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        when(userService.getById(10L,userTest)).thenReturn(Optional.of(userTest));
        assertAll(
                ()->assertEquals(200,userController.getUserById(10L,userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserById(10L,userTest).getBody()),
                ()->assertEquals(10L,Objects.requireNonNull(userController.getUserById(10L,userTest)
                        .getBody()).getId().longValue())
        );
        verify(converters, times(3)).userToDTO(userTest);
    }

    @Test
    void testFailGetUserById() {

        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        when(userService.getById(10L,userTest)).thenReturn(Optional.of(userTest));
        assertAll(
                ()->assertEquals(404,userController.getUserById(20L,userTest).getStatusCodeValue()),
                ()->assertNull(userController.getUserById(20L,userTest).getBody())
        );
        verify(converters, times(0)).userToDTO(userTest);
    }

    @Test
    void testSuccessGetUserByFirstname() {
        List<User> userList=new ArrayList<>();
        userList.add(userTest);

        when(userService.getByFirstname("Susan",userTest)).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByFirstname("Susan",userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFirstname("Susan",userTest).getBody()),
                ()->assertFalse(Objects.requireNonNull(userController.getUserByFirstname("Susan",userTest).getBody()).isEmpty()),
                ()->assertEquals("Susan",Objects.requireNonNull(userController.getUserByFirstname("Susan",userTest)
                        .getBody()).get(0).getPersonalData().getFirstname())
        );
        verify(converters, times(4)).userToDTO(userTest);
    }
    @Test
    void testFailGetUserByFirstname() {
        List<User> emptyUserList=new ArrayList<>();
        when(userService.getByFirstname("Sussan",userTest)).thenReturn(emptyUserList);
        assertAll(
                ()->assertNotNull(userController.getUserByFirstname("Sussan",userTest)),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByFirstname("Sussan",userTest).getBody()).isEmpty())
        );
        verify(converters, times(0)).userToDTO(userTest);
    }

    @Test
    void testSuccessGetUserByLastname() {
        List<User> userList=new ArrayList<>();
        userList.add(userTest);

        when(userService.getByLastname("Barrow",userTest)).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByLastname("Barrow",userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByLastname("Barrow",userTest).getBody()),
                ()->assertFalse(Objects.requireNonNull(userController.getUserByLastname("Barrow",userTest).getBody()).isEmpty()),
                ()->assertEquals("Barrow",Objects.requireNonNull(userController.getUserByLastname("Barrow",userTest)
                        .getBody()).get(0).getPersonalData().getLastname())
        );
        verify(converters, times(4)).userToDTO(userTest);

    }

    @Test
    void testFailGetUserByLastname() {
        List<User> emptyUserList=new ArrayList<>();
        when(userService.getByLastname("Sussan",userTest)).thenReturn(emptyUserList);
        assertAll(
                ()->assertEquals(200,userController.getUserByLastname("Sussan",userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByLastname("Sussan",userTest)),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByLastname("Sussan",userTest).getBody()).isEmpty())
        );
        verify(converters, times(0)).userToDTO(userTest);

    }

    @Test
    void testSuccessGetUserByFullname() {
        List<User> userList=new ArrayList<>();
        userList.add(userTest);
        when(userService.getByFullName("Susan","Barrow",userTest)).thenReturn(userList);
        when(converters.userToDTO(userTest)).thenReturn(userTestDTO);
        assertAll(
                ()->assertEquals(200,userController.getUserByFullName("Susan","Barrow",userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFullName("Susan","Barrow",userTest)),
                ()->assertEquals("Susan",Objects.requireNonNull(userController
                        .getUserByFullName("Susan","Barrow",userTest).getBody())
                        .get(0).getPersonalData().getFirstname()),
                ()->assertEquals("Barrow",Objects.requireNonNull(userController
                        .getUserByFullName("Susan","Barrow",userTest).getBody()).get(0).getPersonalData().getLastname())
        );
        verify(converters, times(4)).userToDTO(userTest);
    }

    @Test
    void testFailGetUserByFullname() {
        List<User> emptyUserList=new ArrayList<>();
        when(userService.getByFullName("Susan","Barrow",userTest)).thenReturn(emptyUserList);
        assertAll(
                ()->assertEquals(200,userController.getUserByFullName("Susan","Barrow",userTest).getStatusCodeValue()),
                ()->assertNotNull(userController.getUserByFullName("Susan","Barrow",userTest)),
                ()->assertTrue(Objects.requireNonNull(userController.getUserByFullName("Susan","Barrow",userTest).getBody()).isEmpty())
        );
        verify(converters, times(0)).userToDTO(userTest);
    }

    @Test
    void testSuccessUpdateUser() {

        when(userService.saveUser(userTest)).thenReturn(Optional.of(userTest));
        when(userService.getById(10L,userTest)).thenReturn(Optional.of(userTest));

        assertEquals(202,userController.updateUser(userTestDTO,userTest).getStatusCodeValue());
        verify(converters, times(1)).updateUserWithDTO(userTestDTO,userTest);

    }

    @Test
    void testFailUpdateUser() {

        when(userService.saveUser(userTest)).thenReturn(Optional.of(userTest));
        when(userService.getById(20L,userTest)).thenReturn(Optional.of(userTest));

        assertEquals(404,userController.updateUser(userTestDTO,userTest).getStatusCodeValue());
        verify(converters, times(0)).updateUserWithDTO(userTestDTO,userTest);

    }
}
