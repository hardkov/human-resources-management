package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.LeaveDTO;
import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.LeaveService;
import com.agh.hr.persistence.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LeaveController implements SecuredRestController {
    private final Converters converters;
    private final LeaveService leaveService;
    private final UserService userService;

    @Autowired
    public LeaveController(Converters converters, LeaveService leaveService, UserService userService) {
        this.converters = converters;
        this.leaveService = leaveService;
        this.userService = userService;
    }

    //// CREATE
    @PostMapping(value = "/leave/user/{userId}")
    @Operation(summary = "Inserting new leave for user by userID",
               responses = {
                @ApiResponse(responseCode = "200", description = "Created leave's DTO"),
                @ApiResponse(responseCode = "404", description = "User not found", content = @Content()),
                @ApiResponse(responseCode = "400", description = "Leave could not be saved", content = @Content()),
               })
    public ResponseEntity<LeaveDTO> insertLeave(@PathVariable Long userId, @RequestBody LeaveDTO leaveDTO) {
        Optional<User> userOpt = userService.getById(userId);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            Leave leave = converters.DTOToLeave(leaveDTO);
            leave.setUser(user);
            Optional<Leave> insertedLeaveOpt = leaveService.saveLeave(leave);
            return insertedLeaveOpt
                    .map(insertedLeave -> ResponseEntity.ok(converters.leaveToDTO(insertedLeave)))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //// READ
    @GetMapping(value = "/leave")
    @Operation(summary = "Reading all leaves",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of all leaves")
               })
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        return ResponseEntity.ok(
                leaveService.getAllLeaves().stream().map(converters::leaveToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/leave/user/{userId}")
    @Operation(summary = "Reading user's leaves by userID",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of user's leaves' DTOs"),
                @ApiResponse(responseCode = "404", description = "User not found", content = @Content())
               })
    public ResponseEntity<List<LeaveDTO>> getLeavesByUserId(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getById(userId);
        return userOpt
                .map(user -> ResponseEntity.ok(
                        user
                                .getLeaves().stream()
                                .map(converters::leaveToDTO)
                                .collect(Collectors.toList())
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //// UPDATE
    @PutMapping(value = "/leave")
    @Operation(summary = "Updating single leave (if leave with specified ID exists)",
               responses = {
                @ApiResponse(responseCode = "200", description = "Leave updated successfully"),
                @ApiResponse(responseCode = "404", description = "Leave not found"),
                @ApiResponse(responseCode = "400", description = "Leave could not be saved")
               })
    public ResponseEntity<Void> updateLeave(@RequestBody LeaveDTO leaveDTO) {
        Optional<Leave> leaveOpt = leaveService.getById(leaveDTO.getId());
        if(leaveOpt.isPresent()){
            Leave leave = leaveOpt.get();
            converters.updateLeaveWithDTO(leaveDTO, leave);
            return leaveService.saveLeave(leave).isPresent() ?
                    ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    //// DELETE
    @DeleteMapping(value = "/leave/{id}")
    @Operation(summary = "Deleting leave with leaveID")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.ok().build();
    }
}
