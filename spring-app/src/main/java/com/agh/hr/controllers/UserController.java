package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController implements SecuredRestController {
    private final Converters converters;
    private final UserService userService;

    @Autowired
    public UserController(Converters converters, UserService userService) {
        this.converters = converters;
        this.userService = userService;
    }

    //// CREATE
    @PostMapping(value = "/user")
    @Operation(summary = "Endpoint responsible for inserting users.. (Just an example how to add docs!)")
    public ResponseEntity<UserDTO> insertUser(@RequestBody UserDTO userDTO) {
        val user = converters.DTOToUser(userDTO);
        val insertedUserOpt = userService.saveUser(user);
        return insertedUserOpt
                .map(insertedUser -> ResponseEntity.ok(converters.userToDTO(insertedUser)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //// READ
    @GetMapping(value = "/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers().stream().map(converters::userToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        val userOpt = userService.getById(id);
        return userOpt
                .map(user -> ResponseEntity.ok(converters.userToDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
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
            converters.updateUserWithDTO(userDTO, user);
            userService.saveUser(user);
            return userService.saveUser(user).isPresent() ?
                    ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
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
}
