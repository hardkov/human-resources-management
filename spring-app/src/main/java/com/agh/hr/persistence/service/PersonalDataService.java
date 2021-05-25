package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.PersonalData;
import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.PersonalDataRepository;
import com.agh.hr.persistence.repository.UserRepository;
import com.agh.hr.persistence.service.permission.Auth;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Optional<PersonalData> savePersonalData(PersonalData data,boolean isNew) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<User> user=userRepository.getUserByPersonalDataId(data.getId());
        if(isNew&&!(Auth.getAdd(userAuth)))
            return Optional.empty();
        else if(!isNew &&user.isPresent()&&!(Auth.getWriteIds(userAuth).contains(user.get().getId())))
            return Optional.empty();
        try {
            return Optional.of(personalDataRepository.save(data));
        }catch(Exception e){return Optional.empty();}
    }

    public Optional<PersonalData> getById(Long id) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findById(id,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getAllPersonalData() {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findAll(Auth.getReadIds(userAuth));
    }

    public ResponseEntity<Void> deletePersonalData(Long dataId) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<User> user=userRepository.getUserByPersonalDataId(dataId);
        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(user.get().getId())))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        personalDataRepository.deleteById(dataId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<PersonalData> getByFirstname(String firstname) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByFirstname(firstname,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByLastname(String lastname) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByLastname(lastname,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByFullName(String firstname,String lastname) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByFirstnameAndLastname(firstname,lastname,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByAddress(String address) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByAddress(address,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByPhone(String phone) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByPhoneNumber(phone,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByEmail(String email) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByEmail(email,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByBirthdateEquals(LocalDate date) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByBirthdateEquals(date,Auth.getReadIds(userAuth));
    }

    public List<PersonalData> getByBirthdateBetween(LocalDate before,LocalDate after) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return personalDataRepository.findByBirthdateBetween(before,after,Auth.getReadIds(userAuth));
    }
}
