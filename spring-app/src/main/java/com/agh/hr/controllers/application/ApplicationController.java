package com.agh.hr.controllers.application;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.model.payload.BonusApplicationPayload;
import com.agh.hr.model.payload.DelegationApplicationPayload;
import com.agh.hr.model.payload.LeaveApplicationPayload;
import com.agh.hr.persistence.dto.BonusApplicationDTO;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.DelegationApplicationDTO;
import com.agh.hr.persistence.dto.LeaveApplicationDTO;
import com.agh.hr.persistence.service.application.IApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping( "/api/employee/application")
public class ApplicationController implements SecuredRestController {

    private final IApplicationService applicationService;

    private final Converters converters;

    @Autowired
    public ApplicationController(Converters converters,
                                 IApplicationService applicationService) {
        this.converters = converters;
        this.applicationService = applicationService;
    }



    // --------------------------------------------------------------------
    //                          GET
    // --------------------------------------------------------------------

    @GetMapping(value = "delegation-application")
    @Operation(
            summary = "Fetch DelegationApplications for logged-in user",
            responses = {
                @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO"),
                @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<List<DelegationApplicationDTO>> getDelegationApplications(Principal principal) {
        val user = converters.toUserDTO(principal);
        val applications = applicationService.getDelegationApplications(user);

        return ResponseEntity.ok(applications);
    }

    @GetMapping(value = "leave-application")
    @Operation(
            summary = "Fetch LeaveApplications for logged-in user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<List<LeaveApplicationDTO>> getLeaveApplications(Principal principal) {
        val user = converters.toUserDTO(principal);
        val applications = applicationService.getLeaveApplications(user);

        return ResponseEntity.ok(applications);
    }

    @GetMapping( "bonus-application")
    @Operation(
            summary = "Update status of bonus application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "BonusApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<List<BonusApplicationDTO>> getBonusApplications(Principal principal) {
        val user = converters.toUserDTO(principal);
        val applications = applicationService.getBonusApplications(user);

        return ResponseEntity.ok(applications);
    }



    // --------------------------------------------------------------------
    //                          PUT
    // --------------------------------------------------------------------

    @PutMapping(value = "leave-application")
    @Operation(
            summary = "Submit a leave application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "LeaveApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<LeaveApplicationDTO> putLeaveApplication(
            Principal principal,
            @RequestBody LeaveApplicationPayload payload) {
        val user = converters.toUserDTO(principal);
        val dto = applicationService.createLeaveApplication(user, payload);

        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "bonus-application")
    @Operation(
            summary = "Submit a bonus application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "BonusApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<BonusApplicationDTO> putBonusApplication(
            Principal principal,
            @RequestBody BonusApplicationPayload payload) {
        val user = converters.toUserDTO(principal);
        val dto = applicationService.createBonusApplication(user, payload);

        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "delegation-application")
    @Operation(
            summary = "Submit a delegation application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<DelegationApplicationDTO> putDelegationApplication(
            Principal principal,
            @RequestBody DelegationApplicationPayload payload) {
        val user = converters.toUserDTO(principal);
        val dto = applicationService.createDelegationApplication(user, payload);

        return ResponseEntity.ok(dto);
    }

}
