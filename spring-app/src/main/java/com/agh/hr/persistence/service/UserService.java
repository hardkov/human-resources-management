package com.agh.hr.persistence.service;

import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;
    private final RoleService roleService;
    private final Converters converters;

    @Autowired
    public UserService(UserRepository userRepository,RoleService roleService, Converters converters){
        this.userRepository=userRepository;
        this.roleService=roleService;
        this.converters = converters;
    }

    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        val userAuth=Auth.getCurrentUser();
        Optional<User> userOpt = getRawById(userDTO.getId());
        if(!userOpt.isPresent())
            return Optional.empty();
        User user=userOpt.get();
        if(!roleService.isAdmin(userAuth))
            if(!Auth.getWriteIds(userAuth).contains(user.getId()))
                return Optional.empty();
        converters.updateUserWithDTO(userDTO,user);
        val result= Optional.of(userRepository.save(user));
        return result.map(converters::userToDTO);
    }

    public Optional<UserDTO> saveUser(UserDTO userDTO) {
        User user=converters.DTOToUser(userDTO);
        val userAuth=Auth.getCurrentUser();

        if(!roleService.isAdmin(userAuth))
            if(!Auth.getAdd(userAuth))
                return Optional.empty();

        user.setId(0L);
        try {
                val result= Optional.of(userRepository.save(user));
                userAuth.getPermissions().addToWrite(result.get().getId());
                userRepository.save(userAuth);
                return result.map(converters::userToDTO);
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserDTO> getById(Long id) {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth))
            return userRepository.findByIdAdmin(id).map(converters::userToDTO);
        return userRepository.findById(id,Auth.getReadIds(userAuth)).map(converters::userToDTO);
    }

    public Optional<User> getRawById(Long id) {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth))
            return userRepository.findByIdAdmin(id);
        return userRepository.findById(id,Auth.getReadIds(userAuth));
    }

    public List<UserDTO> getAllUsers() {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth))
            return userRepository.findAllAdmin().stream()
                    .map(converters::userToDTO)
                    .collect(Collectors.toList());
        return userRepository.findAll(Auth.getReadIds(userAuth)).stream()
                .map(converters::userToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Void> deleteUser(Long userId) {
        val userAuth=Auth.getCurrentUser();
        if(!roleService.isAdmin(userAuth))
            if(!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(userId)))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        userRepository.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<UserDTO> getByFirstname(String firstname) {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth))
            return userRepository.findByFirstnameAdmin(firstname).stream()
                    .map(converters::userToDTO)
                    .collect(Collectors.toList());
        return userRepository.findByPersonalData_Firstname(firstname,Auth.getReadIds(userAuth)).stream()
                .map(converters::userToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getByLastname(String lastname) {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth))
            return userRepository.findByLastnameAdmin(lastname).stream()
                    .map(converters::userToDTO)
                    .collect(Collectors.toList());
        return userRepository.findByPersonalData_Lastname(lastname,Auth.getReadIds(userAuth)).stream()
                .map(converters::userToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getByFullName(String firstname,String lastname) {
        val userAuth=Auth.getCurrentUser();
        if(roleService.isAdmin(userAuth))
            return userRepository.findByFirstnameAndLastnameAdmin(firstname,lastname).stream()
                    .map(converters::userToDTO)
                    .collect(Collectors.toList());
        return userRepository.findByPersonalData_FirstnameAndPersonalData_Lastname
                (firstname,lastname,Auth.getReadIds(userAuth)).stream()
                .map(converters::userToDTO)
                .collect(Collectors.toList());
    }



}
