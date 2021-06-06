package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.ContractDTO;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.LeaveDTO;
import com.agh.hr.persistence.dto.validation.groups.UpdateGroup;
import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.ContractService;
import com.agh.hr.persistence.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ContractController implements SecuredRestController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    //// CREATE
    @PostMapping(value = "/contract/user/{userId}")
    @Operation(summary = "Inserting single contract",
               responses = {
                @ApiResponse(responseCode = "200", description = "Created contract's DTO"),
                @ApiResponse(responseCode = "400", description = "Contract could not be created", content = @Content())
               })
    public ResponseEntity<ContractDTO> insertContract(@Valid @RequestBody ContractDTO contractDTO, @PathVariable Long userId) {
        Optional<ContractDTO> insertedContractOpt = contractService.saveContract(contractDTO,userId);
        return insertedContractOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //// READ
    @GetMapping(value = "/contract")
    @Operation(summary = "Reading list of all contracts within caller read permissions",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of all contracts' DTOs")
               })
    public ResponseEntity<List<ContractDTO>> getAllUsers() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @GetMapping(value = "contract/user/{userId}")
    @Operation(summary = "Reading user's contracts by userID",
               responses = {
                @ApiResponse(responseCode = "200", description = "User's contracts' DTOs"),
               })
    public ResponseEntity<List<ContractDTO>> getContractsByUserId(@PathVariable Long userId) {
            return ResponseEntity.ok(contractService.getByUserId(userId));

    }

    //// UPDATE
    @PutMapping(value = "/contract")
    @Operation(summary = "Updating contract (if exists)",
               responses = {
                @ApiResponse(responseCode = "200", description = "Request accepted for processing"),
                @ApiResponse(responseCode = "400", description = "Contract could not be updated")
               })
    public ResponseEntity<Void> updateContract(@Valid @Validated({UpdateGroup.class}) @RequestBody ContractDTO contractDTO) {
        return contractService.updateContract(contractDTO).isPresent() ?
                ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
    }

    //// DELETE
    @DeleteMapping(value = "/contract/{id}")
    @Operation(summary = "Deleting contract with ID")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        if(contractService.deleteContract(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }
}
