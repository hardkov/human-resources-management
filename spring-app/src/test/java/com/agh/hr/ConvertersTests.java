package com.agh.hr;
import static org.junit.jupiter.api.Assertions.*;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.LeaveDTO;
import com.agh.hr.persistence.dto.PersonalDataDTO;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class ConvertersTests {

    @Autowired
    private Converters converters;


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
        personalDataDTO.setFirstname("Susan");
        personalDataDTO.setLastname("Barrow");
        userTestDTO.setPersonalData(personalDataDTO);
        userTestDTO.setId(10L);
    }

    static class LeaveTest extends Leave{}
    static class UserTest extends User{}
    static class PersonalDataTest extends PersonalData{}

    @Test
    void testDTOToUser() {
        assertEquals(userTest,converters.DTOToUser(userTestDTO));
    }

    @Test
    void testUserToDTO() {
        assertEquals(userTestDTO,converters.userToDTO(userTest));
    }

    @Test
    void testUpdateUserWithDTO() {
        userTestDTO.setId(20L);
        converters.updateUserWithDTO(userTestDTO,userTest);
        assertEquals(20L,userTest.getId().longValue());
    }

    @Test
    void testDTOToPersonalData(){
        assertEquals(userTest.getPersonalData(),converters.DTOToPersonalData(userTestDTO.getPersonalData()));
    }

    @Test
    void testPersonalDataToDTO(){
        assertEquals(userTestDTO.getPersonalData(),converters.personalDataToDTO(userTest.getPersonalData()));
    }

    @Test
    void testUpdatePersonalDataWithDTO(){
        userTestDTO.getPersonalData().setLastname("Harvey");
        converters.updatePersonalDataWithDTO(userTestDTO.getPersonalData(),userTest.getPersonalData());
        assertEquals("Harvey",userTest.getPersonalData().getLastname());
    }

    @Test
    void testDTOToLeave(){
        Leave leave=new LeaveTest();
        leave.setStartDate(LocalDate.of(2020,12,12));
        LeaveDTO leaveDTO=new LeaveDTO();
        leaveDTO.setStartDate(LocalDate.of(2020,12,12));
        assertEquals(leave,converters.DTOToLeave(leaveDTO));
    }

    @Test
    void testLeaveToDTO(){
        Leave leave=new LeaveTest();
        leave.setStartDate(LocalDate.of(2020,12,12));
        leave.setUser(userTest);
        LeaveDTO leaveDTO=new LeaveDTO();
        leaveDTO.setStartDate(LocalDate.of(2020,12,12));
        leaveDTO.setUser(userTestDTO);
        assertEquals(leaveDTO,converters.leaveToDTO(leave));
    }

    @Test
    void testUpdateLeaveWithDTO(){
        Leave leave=new LeaveTest();
        leave.setStartDate(LocalDate.of(2020,12,12));
        LeaveDTO leaveDTO=new LeaveDTO();
        leaveDTO.setStartDate(LocalDate.of(2020,12,16));
        converters.updateLeaveWithDTO(leaveDTO,leave);
        assertEquals(LocalDate.of(2020,12,16),leave.getStartDate());
    }

}

