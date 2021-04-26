package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.service.PersonalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonalDataController implements SecuredRestController {

    @Autowired
    private PersonalDataService dataService;

    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<PersonalData>> getPersonalDataById(@PathVariable Long id) {
        return ResponseEntity.ok(dataService.getById(id));
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getAllPersonalData() {
        return ResponseEntity.ok(dataService.getAllPersonalData());
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePersonalData(@PathVariable Long id) {
        dataService.deletePersonalData(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public ResponseEntity<Void> insertPersonalData(@RequestBody PersonalData data) {
        return dataService.savePersonalData(data)?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public  ResponseEntity<Void> updatePersonalData(@RequestBody PersonalData data) {
        return dataService.savePersonalData(data)?ResponseEntity.accepted().build():ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/dataByFirstname/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByFirstname(@PathVariable String name) {
        return ResponseEntity.ok(dataService.getByFirstname(name));
    }

    @RequestMapping(value = "/dataByLastname/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByLastname(@PathVariable String name) {
        return ResponseEntity.ok(dataService.getByLastname(name));
    }

    @RequestMapping(value = "/dataByFullname/{firstname}/{lastname}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return ResponseEntity.ok(dataService.getByFullName(firstname,lastname));
    }

    @RequestMapping(value = "/dataByAddress/{address}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByAddress(@PathVariable String address) {
        return ResponseEntity.ok(dataService.getByAddress(address));
    }

    @RequestMapping(value = "/dataByEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByEmail(@PathVariable String email) {
        return ResponseEntity.ok(dataService.getByEmail(email));
    }

    @RequestMapping(value = "/dataByPhone/{phone}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(dataService.getByPhone(phone));
    }

    @RequestMapping(value = "/dataByBirthdateEquals/{date}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByBirthdateEquals(@PathVariable LocalDate date) {
        return ResponseEntity.ok(dataService.getByBirthdateEquals(date));
    }

    @RequestMapping(value = "/dataByBirthdateBetween/{before}/{after}", method = RequestMethod.GET)
    public ResponseEntity<List<PersonalData>> getPersonalDataByBirthdateBetween(@PathVariable LocalDate before,@PathVariable LocalDate after) {
        return ResponseEntity.ok(dataService.getByBirthdateBetween(before,after));
    }

}
