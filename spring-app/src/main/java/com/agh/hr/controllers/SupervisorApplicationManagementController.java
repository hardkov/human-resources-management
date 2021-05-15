package com.agh.hr.controllers;

import com.agh.hr.persistence.dto.BonusApplicationDTO;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.DelegationApplicationDTO;
import com.agh.hr.persistence.dto.LeaveApplicationDTO;
import com.agh.hr.persistence.service.application.ISupervisorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController("api/supervisor/application-management")
public class SupervisorApplicationManagementController {

    private final ISupervisorApplicationService applicationService;

    private final Converters converters;

    @Autowired
    public SupervisorApplicationManagementController(Converters converters,
                                                     ISupervisorApplicationService applicationService) {
        this.converters = converters;
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/delegations", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @Operation(
            summary = "Fetch DelegationApplications for subordinates",
            responses = {
                @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO")
            })
    public ResponseEntity<List<DelegationApplicationDTO>> getDelegationApplications(Principal principal) {
        val supervisor = converters.toUserDTO(principal);
        val applications = applicationService.getDelegationApplications(supervisor);

        return ResponseEntity.ok(applications);
    }

    @RequestMapping(value = "/leaves", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @Operation(
            summary = "Fetch LeaveApplications for subordinates",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO")
            })
    public ResponseEntity<List<LeaveApplicationDTO>> getLeaveApplications(Principal principal) {
        val supervisor = converters.toUserDTO(principal);
        val applications = applicationService.getLeaveApplications(supervisor);

        return ResponseEntity.ok(applications);
    }

    @RequestMapping(value = "/bonuses", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @Operation(
            summary = "Fetch BonusApplications for subordinates",
            responses = {
                    @ApiResponse(responseCode = "200", description = "BonusApplicationDTO")
            })
    public ResponseEntity<List<BonusApplicationDTO>> getSubordinatesBonusApplications(Principal principal) {
        val supervisor = converters.toUserDTO(principal);
        val applications = applicationService.getBonusApplications(supervisor);

        return ResponseEntity.ok(applications);
    }

}
