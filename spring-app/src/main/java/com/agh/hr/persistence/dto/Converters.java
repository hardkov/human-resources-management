package com.agh.hr.persistence.dto;

import com.agh.hr.persistence.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Converters {
    private final ModelMapper modelMapper;

    @Autowired
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

    public User DTOToUser(UserInsertionDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        PersonalData personalData=DTOToPersonalData(userDTO.getPersonalData());
        user.setPersonalData(personalData);
        return user;
    }

    public UserDTO InsertionDTOToDTO(UserInsertionDTO userInsertionDTO) {
        UserDTO userDTO = modelMapper.map(userInsertionDTO, UserDTO.class);
        userDTO.setPersonalData(userInsertionDTO.getPersonalData());
        return userDTO;
    }

    public void updateUserWithDTO(UserDTO userDTO, User user) {
        updatePersonalDataWithDTO(userDTO.personalData, user.getPersonalData());
        modelMapper.map(userDTO, user);
    }

    public UserDTO userToDTO(User user) {
        user.getPersonalData().setUser(null);
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

    //// CONTRACT
    public Contract DTOToContract(ContractDTO contractDTO) {
        return modelMapper.map(contractDTO, Contract.class);
    }

    public void updateContractWithDTO(ContractDTO contractDTO, Contract contract) {
        modelMapper.map(contractDTO, contract);
    }

    public ContractDTO contractToDTO(Contract contract) {
        ContractDTO contractDTO = modelMapper.map(contract, ContractDTO.class);
        contractDTO.setUser(userToDTO(contract.getUser()));
        return contractDTO;
    }

    ////PERMISSION
    public Permission DTOToPermission(PermissionDTO permissionDTO) {
        return modelMapper.map(permissionDTO, Permission.class);
    }

    public void updatePermissionWithDTO(PermissionDTO permissionDTO, Permission permission) {
        modelMapper.map(permissionDTO, permission);
        permission.setWrite(permissionDTO.getWrite());
        permission.setRead(permissionDTO.getRead());
        permission.setAdd(permissionDTO.isAdd());
    }

    public PermissionDTO permissionToDTO(Permission permission) {
        PermissionDTO permissionDTO = modelMapper.map(permission, PermissionDTO.class);
        permissionDTO.setUser(userToDTO(permission.getUser()));
        return permissionDTO;
    }
}
