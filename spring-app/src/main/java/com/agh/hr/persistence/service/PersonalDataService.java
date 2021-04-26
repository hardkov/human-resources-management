package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.repository.PersonalDataRepository;
import com.agh.hr.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonalDataService {


    private final PersonalDataRepository personalDataRepository;

    @Autowired
    public PersonalDataService(PersonalDataRepository personalDataRepository){
        this.personalDataRepository=personalDataRepository;
    }

    public Optional<PersonalData> savePersonalData(PersonalData data) {
        try {
            return Optional.of(personalDataRepository.save(data));
        }catch(Exception e){return Optional.empty();}
    }

    public Optional<PersonalData> getById(Long id) {
        return personalDataRepository.findById(id);
    }

    public List<PersonalData> getAllPersonalData() {
        return personalDataRepository.findAll();
    }

    public void deletePersonalData(Long dataId) {
            personalDataRepository.deleteById(dataId);
    }

    public List<PersonalData> getByFirstname(String firstname) {
        return personalDataRepository.findByFirstname(firstname);
    }

    public List<PersonalData> getByLastname(String lastname) {
        return personalDataRepository.findByLastname(lastname);
    }

    public List<PersonalData> getByFullName(String firstname,String lastname) {
        return personalDataRepository.findByFirstnameAndLastname(firstname,lastname);
    }

    public List<PersonalData> getByAddress(String address) {
        return personalDataRepository.findByAddress(address);
    }

    public List<PersonalData> getByPhone(String phone) {
        return personalDataRepository.findByPhoneNumber(phone);
    }

    public List<PersonalData> getByEmail(String email) {
        return personalDataRepository.findByEmail(email);
    }

    public List<PersonalData> getByBirthdateEquals(LocalDate date) {
        return personalDataRepository.findByBirthdateEquals(date);
    }

    public List<PersonalData> getByBirthdateBetween(LocalDate before,LocalDate after) {
        return personalDataRepository.findByBirthdateBetween(before,after);
    }
}
