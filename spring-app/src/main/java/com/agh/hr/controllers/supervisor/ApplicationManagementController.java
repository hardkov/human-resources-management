package com.agh.hr.controllers.supervisor;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.model.payload.UpdateApplicationStatusPayload;
import com.agh.hr.persistence.dto.BonusApplicationDTO;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.DelegationApplicationDTO;
import com.agh.hr.persistence.dto.LeaveApplicationDTO;
import com.agh.hr.persistence.service.application.ISupervisorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping( "/api/supervisor/application-management")
public class ApplicationManagementController implements SecuredRestController {

    private final ISupervisorApplicationService applicationService;

    private final Converters converters;

    @Autowired
    public ApplicationManagementController(Converters converters,
                                           ISupervisorApplicationService applicationService) {
        this.converters = converters;
        this.applicationService = applicationService;
    }

    @GetMapping(value = "delegation-application")
    @Operation(
            summary = "Fetch DelegationApplications for subordinates",
            responses = {
                @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO"),
                @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<List<DelegationApplicationDTO>> getDelegationApplications(Principal principal) {
        val supervisor = converters.toUserDTO(principal);
        val applications = applicationService.getDelegationApplications(supervisor);

        return ResponseEntity.ok(applications);
    }

    @GetMapping(value = "/leave-application")
    @Operation(
            summary = "Fetch LeaveApplications for subordinates",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<List<LeaveApplicationDTO>> getLeaveApplications(Principal principal) {
        val supervisor = converters.toUserDTO(principal);
        val applications = applicationService.getLeaveApplications(supervisor);

        return ResponseEntity.ok(applications);
    }

    @GetMapping( "/bonus-application")
    @Operation(
            summary = "Update status of delegation application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "BonusApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<List<BonusApplicationDTO>> getSubordinatesBonusApplications(Principal principal) {
        val supervisor = converters.toUserDTO(principal);
        val applications = applicationService.getBonusApplications(supervisor);

        return ResponseEntity.ok(applications);
    }

    @PostMapping(value = "leave-application")
    @Operation(
            summary = "Update status of leave application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DelegationApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<LeaveApplicationDTO> updateLeaveApplicationStatus(
            Principal principal,
            @Valid @RequestBody UpdateApplicationStatusPayload payload) {

        val supervisor = converters.toUserDTO(principal);
        val dto = applicationService.updateLeaveApplicationStatus(supervisor, payload);

        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "bonus-application")
    @Operation(
            summary = "Update status of bonus application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "BonusApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<BonusApplicationDTO> updateBonusApplicationStatus(
            Principal principal,
            @Valid @RequestBody  UpdateApplicationStatusPayload payload) {

        val supervisor = converters.toUserDTO(principal);
        val dto = applicationService.updateBonusApplicationStatus(supervisor, payload);

        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "delegation-application")
    @Operation(
            summary = "Update status of delegation application",
            responses = {
                    @ApiResponse(responseCode = "200", description = "BonusApplicationDTO"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content())
            })
    public ResponseEntity<DelegationApplicationDTO> updateDelegationApplicationStatus(
            Principal principal,
            @Valid @RequestBody UpdateApplicationStatusPayload payload) {

        val supervisor = converters.toUserDTO(principal);
        val dto = applicationService.updateDelegationApplicationStatus(supervisor, payload);

        return ResponseEntity.ok(dto);
    }

}
