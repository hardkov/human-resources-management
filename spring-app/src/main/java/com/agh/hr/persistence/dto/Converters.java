package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Converters {
    private final ModelMapper modelMapper;

    public Converters(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //// USER
    public User DTOToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        PersonalData personalData=DTOToPersonalData(userDTO.personalData);
        user.setPersonalData(personalData);
        return user;
    }

    public void updateUserWithDTO(UserDTO userDTO, User user) {
        updatePersonalDataWithDTO(userDTO.personalData, user.getPersonalData());
        modelMapper.map(userDTO, user);
    }

    public UserDTO userToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    //// PERSONAL DATA
    public PersonalData DTOToPersonalData(PersonalDataDTO personalDataDTO) {
        return modelMapper.map(personalDataDTO, PersonalData.class);
    }

    public void updatePersonalDataWithDTO(PersonalDataDTO personalDataDTO, PersonalData personalData) {
        modelMapper.map(personalDataDTO, personalData);
    }

    public PersonalDataDTO personalDataToDTO(PersonalData personalData) {
        return modelMapper.map(personalData, PersonalDataDTO.class);
    }

    //// LEAVE
    public Leave DTOToLeave(LeaveDTO leaveDTO) {
        return modelMapper.map(leaveDTO, Leave.class);
    }

    public void updateLeaveWithDTO(LeaveDTO leaveDTO, Leave leave) {
        modelMapper.map(leaveDTO, leave);
    }

    public LeaveDTO leaveToDTO(Leave leave) {
        LeaveDTO leaveDTO = modelMapper.map(leave, LeaveDTO.class);
        leaveDTO.setUser(userToDTO(leave.getUser()));
        return leaveDTO;
    }
}
