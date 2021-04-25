package com.agh.hr.controllers;

import com.agh.hr.persistence.DTO.Converters;
import com.agh.hr.persistence.DTO.PersonalDataDTO;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.service.PersonalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PersonalDataController {


    private PersonalDataService dataService;
    private final Converters converters;

    @Autowired
    public PersonalDataController(PersonalDataService dataService,Converters converters) {
        this.dataService = dataService;
        this.converters = converters;
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonalDataDTO> getPersonalDataById(@PathVariable Long id) {
        Optional<PersonalData> personalDataOpt =dataService.getById(id);
        return personalDataOpt.map(data->ResponseEntity.ok(converters.personalDataToDTO(data)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getAllPersonalData() {
        return ResponseEntity.ok(dataService.getAllPersonalData().stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePersonalData(@PathVariable Long id) {
        dataService.deletePersonalData(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public ResponseEntity<PersonalDataDTO> insertPersonalData(@RequestBody PersonalDataDTO dataDTO) {
        PersonalData data = converters.DTOToPersonalData(dataDTO);
        Optional<PersonalData> insertedPersonalDataOpt = dataService.savePersonalData(data);
        return insertedPersonalDataOpt.map(insertedData -> ResponseEntity.ok(converters.personalDataToDTO(insertedData)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public  ResponseEntity<Void> updatePersonalData(@RequestBody PersonalDataDTO dataDTO) {
        Optional<PersonalData> personalDataOpt = dataService.getById(dataDTO.getId());
        if(personalDataOpt.isPresent()){
            PersonalData data = personalDataOpt.get();
            converters.updatePersonalDataWithDTO(dataDTO, data);
            return dataService.savePersonalData(data).isPresent() ?
                    ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/dataByFirstname/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByFirstname(@PathVariable String name) {
        return ResponseEntity.ok(dataService.getByFirstname(name).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByLastname/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByLastname(@PathVariable String name) {
        return ResponseEntity.ok(dataService.getByLastname(name).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByFullname/{firstname}/{lastname}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return ResponseEntity.ok(dataService.getByFullName(firstname,lastname).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByAddress/{address}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByAddress(@PathVariable String address) {
        return ResponseEntity.ok(dataService.getByAddress(address).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByEmail(@PathVariable String email) {
        return ResponseEntity.ok(dataService.getByEmail(email).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByPhone/{phone}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(dataService.getByPhone(phone).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByBirthdateEquals/{date}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByBirthdateEquals(@PathVariable LocalDate date) {
        return ResponseEntity.ok(dataService.getByBirthdateEquals(date).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/dataByBirthdateBetween/{before}/{after}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalDataDTO>> getPersonalDataByBirthdateBetween(@PathVariable LocalDate before,@PathVariable LocalDate after) {
        return ResponseEntity.ok(dataService.getByBirthdateBetween(before,after).stream()
                .map(converters::personalDataToDTO)
                .collect(Collectors.toList()));
    }

}
