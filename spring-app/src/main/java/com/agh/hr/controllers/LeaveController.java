package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.LeaveDTO;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.model.Leave;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.LeaveService;
import com.agh.hr.persistence.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LeaveController implements SecuredRestController {
    private final LeaveService leaveService;

    @Autowired
    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    //// CREATE
    @PostMapping(value = "/leave/user/{userId}")
    @Operation(summary = "Inserting new leave for user by userID",
               responses = {
                @ApiResponse(responseCode = "200", description = "Created leave's DTO"),
                @ApiResponse(responseCode = "400", description = "Leave could not be saved", content = @Content()),
               })
    public ResponseEntity<LeaveDTO> insertLeave(@PathVariable Long userId, @RequestBody LeaveDTO leaveDTO) {
            Optional<LeaveDTO> insertedLeaveOpt = leaveService.saveLeave(leaveDTO,userId);
            return insertedLeaveOpt
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //// READ
    @GetMapping(value = "/leave")
    @Operation(summary = "Reading all leaves",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of all leaves")
               })
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        
        return ResponseEntity.ok(
                leaveService.getAllLeaves()
        );
    }

    @GetMapping(value = "/leave/user/{userId}")
    @Operation(summary = "Reading user's leaves by userID",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of user's leaves' DTOs"),
               })
    public ResponseEntity<List<LeaveDTO>> getLeavesByUserId(@PathVariable Long userId
            ) {
        return ResponseEntity.ok(leaveService.getByUserId(userId));

    }

    //// UPDATE
    @PutMapping(value = "/leave")
    @Operation(summary = "Updating single leave (if leave with specified ID exists)",
               responses = {
                @ApiResponse(responseCode = "200", description = "Leave updated successfully"),
                @ApiResponse(responseCode = "400", description = "Leave could not be saved")
               })
    public ResponseEntity<Void> updateLeave(@RequestBody LeaveDTO leaveDTO) {
        return leaveService.updateLeave(leaveDTO).isPresent() ?
                ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
    }

    //// DELETE
    @DeleteMapping(value = "/leave/{id}")
    @Operation(summary = "Deleting leave with leaveID")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        
        return leaveService.deleteLeave(id);
    }
}
