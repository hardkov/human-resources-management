package com.agh.hr.controllers;

import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.service.PersonalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonalDataController {

    @Autowired
    private PersonalDataService dataService;

    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<PersonalData> getPersonalDataById(@PathVariable Long id) {
        return dataService.getById(id);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public List<PersonalData> getAllPersonalData() {
        return dataService.getAllPersonalData();
    }

    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    public HttpStatus deletePersonalData(@PathVariable Long id) {
        dataService.deletePersonalData(id);
        return HttpStatus.NO_CONTENT;
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public HttpStatus insertPersonalData(@RequestBody PersonalData data) {
        return dataService.savePersonalData(data) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public HttpStatus updatePersonalData(@RequestBody PersonalData data) {
        return dataService.savePersonalData(data) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "/dataByFirstname/{name}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByFirstname(@PathVariable String name) {
        return dataService.getByFirstname(name);
    }

    @RequestMapping(value = "/dataByLastname/{name}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByLastname(@PathVariable String name) {
        return dataService.getByLastname(name);
    }

    @RequestMapping(value = "/dataByFullname/{firstname}/{lastname}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return dataService.getByFullName(firstname,lastname);
    }

    @RequestMapping(value = "/dataByAddress/{address}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByAddress(@PathVariable String address) {
        return dataService.getByAddress(address);
    }

    @RequestMapping(value = "/dataByEmail/{email}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByEmail(@PathVariable String email) {
        return dataService.getByEmail(email);
    }

    @RequestMapping(value = "/dataByPhone/{phone}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByPhone(@PathVariable String phone) {
        return dataService.getByPhone(phone);
    }

    @RequestMapping(value = "/dataByBirthdateEquals/{date}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByBirthdateEquals(@PathVariable LocalDate date) {
        return dataService.getByBirthdateEquals(date);
    }

    @RequestMapping(value = "/dataByBirthdateBetween/{before}/{after}", method = RequestMethod.GET)
    public List<PersonalData> getPersonalDataByBirthdateBetween(@PathVariable LocalDate before,@PathVariable LocalDate after) {
        return dataService.getByBirthdateBetween(before,after);
    }

}
