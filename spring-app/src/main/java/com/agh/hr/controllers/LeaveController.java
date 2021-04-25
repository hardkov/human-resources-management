package com.agh.hr.controllers;

import com.agh.hr.persistence.DTO.Converters;
import com.agh.hr.persistence.DTO.LeaveDTO;
import com.agh.hr.persistence.service.LeaveService;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LeaveController {
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
    public ResponseEntity<LeaveDTO> insertLeave(@PathVariable Long userId, @RequestBody LeaveDTO leaveDTO) {
        val userOpt = userService.getById(userId);
        if(userOpt.isPresent()){
            val user = userOpt.get();
            val leave = converters.DTOToLeave(leaveDTO);
            leave.setUser(user);
            val insertedLeaveOpt = leaveService.saveLeave(leave);
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
    public ResponseEntity<List<LeaveDTO>> getAllLeaves() {
        return ResponseEntity.ok(
                leaveService.getAllLeaves().stream().map(converters::leaveToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/leave/user/{userId}")
    public ResponseEntity<List<LeaveDTO>> getLeavesByUserId(@PathVariable Long userId) {
        val userOpt = userService.getById(userId);
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
    public ResponseEntity<Void> updateLeave(@RequestBody LeaveDTO leaveDTO) {
        val leaveOpt = leaveService.getById(leaveDTO.getId());
        if(leaveOpt.isPresent()){
            val leave = leaveOpt.get();
            converters.updateLeaveWithDTO(leaveDTO, leave);
            return leaveService.saveLeave(leave).isPresent() ?
                    ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        }
        else
            return ResponseEntity.notFound().build();
    }
}
