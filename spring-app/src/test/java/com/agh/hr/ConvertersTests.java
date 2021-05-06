package com.agh.hr;
import static org.junit.jupiter.api.Assertions.*;

import com.agh.hr.config.ModelMapperConfig;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.LeaveDTO;
import com.agh.hr.persistence.dto.PersonalDataDTO;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.val;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ModelMapperConfig.class, Converters.class } )
public class ConvertersTests {

    @Autowired
    private Converters converters;

    private User userTest;
    private UserDTO userTestDTO;

    @BeforeEach
    public void setup() {
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
    void testDTOToUser() {
        assertEquals(userTest, converters.DTOToUser(userTestDTO));
    }

    @Test
    void testUserToDTO() {
        assertEquals(userTestDTO, converters.userToDTO(userTest));
    }

    @Test
    void testUpdateUserWithDTO() {
        userTestDTO.setId(20L);
        converters.updateUserWithDTO(userTestDTO, userTest);

        assertEquals(20L, userTest.getId().longValue());
    }

    @Test
    void testDTOToPersonalData() {
        val actual = converters.DTOToPersonalData(userTestDTO.getPersonalData());
        val expected = userTest.getPersonalData();

        assertEquals(expected, actual);
    }

    @Test
    void testPersonalDataToDTO(){
        val expected = userTestDTO.getPersonalData();
        val actual = converters.personalDataToDTO(userTest.getPersonalData());

        assertEquals(expected, actual);
    }

    @Test
    void testUpdatePersonalDataWithDTO(){
        val expectedLastname = "Harvey";
        userTestDTO.getPersonalData().setLastname(expectedLastname);

        val personalDataDTO = userTestDTO.getPersonalData();
        val personalData = userTest.getPersonalData();
        converters.updatePersonalDataWithDTO(personalDataDTO, personalData);

        val actualLastname = userTest.getPersonalData().getLastname();

        assertEquals(expectedLastname, actualLastname);
    }

    @Test
    void testDTOToLeave(){
        val startDate = LocalDate.of(2020,12,12);
        val leave = Leave.builder()
                .startDate(startDate)
                .build();

        val leaveDTO = LeaveDTO.builder()
                .startDate(startDate)
                .build();

        assertEquals(leave, converters.DTOToLeave(leaveDTO));
    }

    @Test
    void testLeaveToDTO(){
        val startDate = LocalDate.of(2020,12,12);
        val leave = Leave.builder()
                .startDate(startDate)
                .user(userTest)
                .build();

        val leaveDTO = LeaveDTO.builder()
                .startDate(startDate)
                .user(userTestDTO)
                .build();

        assertEquals(leaveDTO, converters.leaveToDTO(leave));
    }

    @Test
    void testUpdateLeaveWithDTO(){
        val startDate = LocalDate.of(2020,12,12);
        val leave = Leave.builder()
                .startDate(startDate)
                .user(userTest)
                .build();

        val updatedStartDate = LocalDate.of(2020,12,16);
        val leaveDTO = LeaveDTO.builder()
                .startDate(updatedStartDate)
                .build();

        converters.updateLeaveWithDTO(leaveDTO,leave);
        assertEquals(updatedStartDate, leave.getStartDate());
    }

}

