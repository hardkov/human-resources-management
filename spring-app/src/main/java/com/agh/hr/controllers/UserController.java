package com.agh.hr.controllers;

import com.agh.hr.persistence.DTO.UserDetailedDTO;
import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.DTO.UserDTO;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    //// CREATE
    @PostMapping(value = "/user")
    public ResponseEntity<Void> insertUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(convertToUser(userDTO))?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/user/{id}/leave")
    public ResponseEntity<Void> insertLeave(@PathVariable Long id, @RequestBody Leave leave) {
        val userOpt = userService.getById(id);
        if(userOpt.isPresent()){
            val user = userOpt.get();
            user.addLeave(leave);
            return userService.saveUser(user) ?
                    ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //// READ
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDetailedDTO> getUserById(@PathVariable Long id) {
        val userOpt = userService.getById(id);
        return userOpt
                .map(user -> ResponseEntity.ok(convertToDetailedDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers().stream().map(this::convertToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/userByFirstname/{name}")
    public ResponseEntity<List<User>> getUserByFirstname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByFirstname(name));
    }

    @GetMapping(value = "/userByLastname/{name}")
    public ResponseEntity<List<User>> getUserByLastname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByLastname(name));
    }

    @GetMapping(value = "/userByFullname/{firstname}/{lastname}")
    public ResponseEntity<List<User>> getUserByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return ResponseEntity.ok(userService.getByFullName(firstname,lastname));
    }

    //// UPDATE
    @PutMapping(value = "/user")
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO) {
        val userOpt = userService.getById(userDTO.getId());
        if(userOpt.isPresent()) {
            val user = userOpt.get();
            val personalData = user.getPersonalData();
            modelMapper.map(userDTO, user);
            modelMapper.map(userDTO.getPersonalData(), personalData);
            user.setPersonalData(personalData);
            userService.saveUser(user);
            return userService.saveUser(user) ?
                    ResponseEntity.accepted().build():ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //// DELETE
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDetailedDTO convertToDetailedDTO(User user) {
        return modelMapper.map(user, UserDetailedDTO.class);
    }

    public User convertToUser(UserDTO userDTO) { return modelMapper.map(userDTO, User.class); }
}
