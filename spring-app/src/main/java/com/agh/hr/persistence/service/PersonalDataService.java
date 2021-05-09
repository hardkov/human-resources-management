package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
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

    public Optional<PersonalData> getById(Long id, User userAuth) {
        return personalDataRepository.findById(id,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getAllPersonalData(User userAuth) {
        return personalDataRepository.findAll(Auth.getReadIds(userAuth));
    }

    public void deletePersonalData(Long dataId) {
            personalDataRepository.deleteById(dataId);
    }

    public List<PersonalData> getByFirstname(String firstname,User userAuth) {
        return personalDataRepository.findByFirstname(firstname,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByLastname(String lastname,User userAuth) {
        return personalDataRepository.findByLastname(lastname,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByFullName(String firstname,String lastname,User userAuth) {
        return personalDataRepository.findByFirstnameAndLastname(firstname,lastname,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByAddress(String address,User userAuth) {
        return personalDataRepository.findByAddress(address,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByPhone(String phone,User userAuth) {
        return personalDataRepository.findByPhoneNumber(phone,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByEmail(String email,User userAuth) {
        return personalDataRepository.findByEmail(email,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByBirthdateEquals(LocalDate date,User userAuth) {
        return personalDataRepository.findByBirthdateEquals(date,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByBirthdateBetween(LocalDate before,LocalDate after,User userAuth) {
        return personalDataRepository.findByBirthdateBetween(before,after,Auth.getReadIds(userAuth));
    }
}
