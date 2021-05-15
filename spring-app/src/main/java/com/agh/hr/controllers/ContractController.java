package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.ContractDTO;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.model.Contract;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.ContractService;
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
@RequestMapping("/api")
public class ContractController implements SecuredRestController {
    private final Converters converters;
    private final ContractService contractService;
    private final UserService userService;

    @Autowired
    public ContractController(Converters converters, ContractService contractService, UserService userService) {
        this.converters = converters;
        this.contractService = contractService;
        this.userService = userService;
    }

    //// CREATE
    @PostMapping(value = "/contract/user/{userId}")
    @Operation(summary = "Inserting single contract",
               responses = {
                @ApiResponse(responseCode = "200", description = "Created contract's DTO"),
                @ApiResponse(responseCode = "400", description = "Contract could not be created", content = @Content())
               })
    public ResponseEntity<ContractDTO> insertContract(@RequestBody ContractDTO contractDTO, @PathVariable Long userId) {
        Optional<User> userOpt = userService.getById(userId);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            Contract contract = converters.DTOToContract(contractDTO);
            System.out.println(contract);
            contract.setUser(user);
            Optional<Contract> insertedContractOpt = contractService.saveContract(contract);
            return insertedContractOpt
                    .map(insertedContract -> ResponseEntity.ok(converters.contractToDTO(insertedContract)))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    //// READ
    @GetMapping(value = "/contract")
    @Operation(summary = "Reading list of all contracts within caller read permissions",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of all contracts' DTOs")
               })
    public ResponseEntity<List<ContractDTO>> getAllUsers() {
        return ResponseEntity.ok(
                contractService.getAllContracts().stream().map(converters::contractToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping(value = "contract/user/{userId}")
    @Operation(summary = "Reading user's contracts by userID",
               responses = {
                @ApiResponse(responseCode = "200", description = "User's contracts' DTOs"),
                @ApiResponse(responseCode = "400", description = "User's contract could not be fetched", content = @Content())
               })
    public ResponseEntity<List<ContractDTO>> getContractsByUserId(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getById(userId);
        return userOpt
                .map(user -> ResponseEntity.ok(
                        user
                                .getContracts().stream()
                                .map(converters::contractToDTO)
                                .collect(Collectors.toList())
                ))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //// UPDATE
    @PutMapping(value = "/contract")
    @Operation(summary = "Updating contract (if exists)",
               responses = {
                @ApiResponse(responseCode = "200", description = "Request accepted for processing"),
                @ApiResponse(responseCode = "400", description = "Contract could not be updated")
               })
    public ResponseEntity<Void> updateContract(@RequestBody ContractDTO contractDTO) {
        Optional<Contract> contractOpt = contractService.getById(contractDTO.getId());
        if(contractOpt.isPresent()) {
            Contract contract = contractOpt.get();
            converters.updateContractWithDTO(contractDTO, contract);
            return contractService.saveContract(contract).isPresent() ?
                    ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
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
