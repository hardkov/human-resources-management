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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Optional<UserDetailedDTO>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id).map(this::convertToDetailedDTO));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers().stream().map(this::convertToDTO)
                        .collect(Collectors.toList())
        );
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Void> insertUser(@RequestBody User user) {
        return userService.saveUser(user)?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/user")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        return userService.saveUser(user)?ResponseEntity.accepted().build():ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/userByFirstname/{name}")
    public ResponseEntity<List<User>> getUserByFirstname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByFirstname(name));
    }

    @RequestMapping(value = "/userByLastname/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByLastname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByLastname(name));
    }

    @GetMapping(value = "/userByFullname/{firstname}/{lastname}")
    public ResponseEntity<List<User>> getUserByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return ResponseEntity.ok(userService.getByFullName(firstname,lastname));
    }

    @PostMapping(value = "/user/{id}/leave")
    public ResponseEntity<Optional<UserDetailedDTO>> insertLeave(@PathVariable Long id, @RequestBody Leave leave) {
        Optional<User> user = userService.getById(id);
        return user.map(u -> {
            u.addLeave(leave);
            return userService.saveUser(u);
        }).orElse(false) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDetailedDTO convertToDetailedDTO(User user) {
        return modelMapper.map(user, UserDetailedDTO.class);
    }


}
