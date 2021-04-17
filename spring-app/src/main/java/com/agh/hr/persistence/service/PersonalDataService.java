package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.repository.PersonalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Transactional
    public boolean savePersonalData(PersonalData data) {
        try {
            personalDataRepository.save(data);
        }catch(Exception e){return false;}
        return true;
    }

    @Transactional
    public Optional<PersonalData> getById(Long id) {
        return personalDataRepository.findById(id);
    }

    @Transactional
    public List<PersonalData> getAllPersonalData() {
        return personalDataRepository.findAll();
    }

    @Transactional
    public void deletePersonalData(Long dataId) {
            personalDataRepository.deleteById(dataId);
    }

    @Transactional
    public List<PersonalData> getByFirstname(String firstname) {
        return personalDataRepository.findByFirstname(firstname);
    }

    @Transactional
    public List<PersonalData> getByLastname(String lastname) {
        return personalDataRepository.findByLastname(lastname);
    }

    @Transactional
    public List<PersonalData> getByFullName(String firstname,String lastname) {
        return personalDataRepository.findByFirstnameAndLastname(firstname,lastname);
    }

    @Transactional
    public List<PersonalData> getByAddress(String address) {
        return personalDataRepository.findByAddress(address);
    }

    @Transactional
    public List<PersonalData> getByPhone(String phone) {
        return personalDataRepository.findByPhoneNumber(phone);
    }

    @Transactional
    public List<PersonalData> getByEmail(String email) {
        return personalDataRepository.findByEmail(email);
    }

    @Transactional
    public List<PersonalData> getByBirthdateEquals(LocalDate date) {
        return personalDataRepository.findByBirthdateEquals(date);
    }

    @Transactional
    public List<PersonalData> getByBirthdateBetween(LocalDate before,LocalDate after) {
        return personalDataRepository.findByBirthdateBetween(before,after);
    }
}
