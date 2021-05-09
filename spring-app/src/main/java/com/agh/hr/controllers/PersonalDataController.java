package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.dto.PersonalDataDTO;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.Auth;
import com.agh.hr.persistence.service.PersonalDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PersonalDataController implements SecuredRestController {

    @Autowired
    private PersonalDataService dataService;
    private final Converters converters;

    @Autowired
    public PersonalDataController(PersonalDataService dataService,Converters converters) {
        this.dataService = dataService;
        this.converters = converters;
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data by personalDataId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Personal data DTO")
            })
    public ResponseEntity<PersonalDataDTO> getPersonalDataById(@PathVariable Long id,
                                                               @AuthenticationPrincipal User userAuth) {
        Optional<PersonalData> personalDataOpt =dataService.getById(id,userAuth);
        return personalDataOpt.map(data->ResponseEntity.ok(converters.personalDataToDTO(data)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @Operation(summary = "Reading all personal data records",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of Personal data records' DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getAllPersonalData(@AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getAllPersonalData(userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/data/{PersonalDataId}", method = RequestMethod.DELETE)
    @Operation(summary = "Deleting personal data with personalDataId")
    public ResponseEntity<Void> deletePersonalData(@PathVariable Long PersonalDataId,@AuthenticationPrincipal User userAuth) {
        return dataService.deletePersonalData(PersonalDataId,userAuth);
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    @Operation(summary = "Updating personal data (if personal data with specified ID exists)",
               responses = {
                @ApiResponse(responseCode = "200", description = "Personal data updated successfully"),
                @ApiResponse(responseCode = "400", description = "Personal could not be updated")
               })
    public  ResponseEntity<Void> updatePersonalData(@RequestBody PersonalDataDTO dataDTO,
                                                    @AuthenticationPrincipal User userAuth) {
        Optional<PersonalData> personalDataOpt = dataService.getById(dataDTO.getId(),userAuth);
        if(personalDataOpt.isPresent()){
            PersonalData data = personalDataOpt.get();
            converters.updatePersonalDataWithDTO(dataDTO, data);
            return dataService.savePersonalData(data,userAuth).isPresent() ?
                    ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @RequestMapping(value = "/dataByFirstname/{name}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with specific firstname",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByFirstname(@PathVariable String name,
                                                                            @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByFirstname(name,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByLastname/{name}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with specific lastname",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByLastname(@PathVariable String name,
                                                                           @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByLastname(name,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByFullname/{firstname}/{lastname}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with specific firstname and lastname",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByFullName(@PathVariable String firstname,
                                                                           @PathVariable String lastname,
                                                                           @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByFullName(firstname,lastname,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByAddress/{address}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with specific address",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByAddress(@PathVariable String address,
                                                                          @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByAddress(address,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByEmail/{email}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with specific email",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByEmail(@PathVariable String email,
                                                                        @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByEmail(email,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByPhone/{phone}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with specific phone number",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByPhone(@PathVariable String phone,
                                                                        @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByPhone(phone,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByBirthdateEquals/{date}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with specific date of birth",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByBirthdateEquals(@PathVariable LocalDate date,
                                                                                  @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByBirthdateEquals(date,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByBirthdateBetween/{before}/{after}", method = RequestMethod.GET)
    @Operation(summary = "Reading personal data records with date of birth between before and after",
               responses = {
                @ApiResponse(responseCode = "200", description = "List of personal data DTOs")
               })
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByBirthdateBetween(@PathVariable LocalDate before,
                                                                                   @PathVariable LocalDate after,
                                                                                   @AuthenticationPrincipal User userAuth) {
        return ResponseEntity.ok(dataService.getByBirthdateBetween(before,after,userAuth).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }
}
