package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.PersonalDataRepository;
import com.agh.hr.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonalDataService {


    private final PersonalDataRepository personalDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public PersonalDataService(PersonalDataRepository personalDataRepository,UserRepository userRepository){
        this.personalDataRepository=personalDataRepository;
        this.userRepository=userRepository;
    }

    public Optional<PersonalData> savePersonalData(PersonalData data, User userAuth) {
        Optional<User> user=userRepository.getUserByPersonalDataId(data.getId());
        if(!user.isPresent())
            return Optional.empty();
        if(!(Auth.getWriteIds(userAuth).contains(user.get().getId())))
            return Optional.empty();
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

    public ResponseEntity<Void> deletePersonalData(Long dataId, User userAuth) {
        Optional<User> user=userRepository.getUserByPersonalDataId(dataId);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(user.get().getId())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        personalDataRepository.deleteById(dataId);
        return ResponseEntity.status(HttpStatus.OK).build();
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
