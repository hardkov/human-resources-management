package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController implements SecuredRestController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //// CREATE
    @PostMapping(value = "/user")
    @Operation(summary = "Inserting single user",
               responses = {
                @ApiResponse(responseCode = "200", description = "Created user's DTO"),
                @ApiResponse(responseCode = "400", description = "User could not be created", content = @Content())
               })
    public ResponseEntity<UserDTO> insertUser(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> insertedUserOpt = userService.saveUser(userDTO);
        return insertedUserOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //// READ
    @GetMapping(value = "/user")
    @Operation(summary = "Reading list of all users within caller read permissions",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of all users' DTOs")
               })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    @GetMapping(value = "/user/{id}")
    @Operation(summary = "Reading user by userID",
               responses = {
                @ApiResponse(responseCode = "200", description = "The user's DTO"),
                @ApiResponse(responseCode = "404", description = "User not found", content = @Content())
               })
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userOpt = userService.getById(id);
        return userOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/userByFirstname/{name}")
    @Operation(summary = "Reading users with specific firstname",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of matched users' DTOs")
               })
    public ResponseEntity<List<UserDTO>> getUserByFirstname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByFirstname(name));
    }

    @GetMapping(value = "/userByLastname/{name}")
    @Operation(summary = "Reading users with specific lastname",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of matched users' DTOs")
               })
    public ResponseEntity<List<UserDTO>> getUserByLastname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByLastname(name));
    }

    @GetMapping(value = "/userByFullname/{firstname}/{lastname}")
    @Operation(summary = "Reading users with specific firstname and lastname",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of matched users' DTOs")
               })
    public ResponseEntity<List<UserDTO>> getUserByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return ResponseEntity.ok(userService.getByFullName(firstname,lastname));
    }

    //// UPDATE
    @PutMapping(value = "/user")
    @Operation(summary = "Updating user with userID (if exists)",
               responses = {
                @ApiResponse(responseCode = "202", description = "Request accepted for processing"),
                @ApiResponse(responseCode = "400", description = "User could not be updated")
               })
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO).isPresent() ?
                ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
    }

    //// DELETE
    @DeleteMapping(value = "/user/{id}")
    @Operation(summary = "Deleting user with userID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
