package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.PermissionDTO;
import com.agh.hr.persistence.dto.UserDTO;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.PermissionService;
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
public class PermissionController implements SecuredRestController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController( PermissionService permissionService,Converters converters) {
        this.permissionService = permissionService;
    }

    @PutMapping(value = "/permission")
    @Operation(summary = "Updates permission",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Request accepted for processing"),
                    @ApiResponse(responseCode = "400", description = "User could not be updated")
            })
    public ResponseEntity<Void> updatePermission(@RequestBody PermissionDTO PermissionDTO) {
        return permissionService.savePermission(PermissionDTO).isPresent() ?
                ResponseEntity.accepted().build() : ResponseEntity.badRequest().build();

    }

    @GetMapping(value = "/permissions")
    @Operation(summary = "Reading permissions of all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permission DTOs"),
            })
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @GetMapping(value = "/permissions/{userId}")
    @Operation(summary = "Reading permission by userID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permission's DTO"),
                    @ApiResponse(responseCode = "404", description = "Permission not found", content = @Content())
            })
    public ResponseEntity<PermissionDTO> getPermissionsByUserId(@PathVariable Long userId) {
        Optional<PermissionDTO> permissionOpt = permissionService.getByUserId(userId);
        return permissionOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}