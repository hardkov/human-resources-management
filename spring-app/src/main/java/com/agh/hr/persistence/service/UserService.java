package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;

    }

    public Optional<User> saveUser(User user,boolean isNew) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(isNew&&!(Auth.getAdd(userAuth)))
            return Optional.empty();
        else if(!isNew &&!(Auth.getWriteIds(userAuth).contains(user.getId())))
            return Optional.empty();
        try {
                val result= Optional.of(userRepository.save(user));
                if(isNew) {
                    userAuth.getPermissions().addToWrite(result.get().getId());
                    val newUserAuth = userRepository.save(userAuth);
                }
                return result;
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getById(Long id) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findById(id,Auth.getReadIds(userAuth));
    }

    public List<User> getAllUsers() {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findAll(Auth.getReadIds(userAuth));
    }

    public ResponseEntity<Void> deleteUser(Long userId) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(!(Auth.getAdd(userAuth)&&Auth.getWriteIds(userAuth).contains(userId)))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        userRepository.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<User> getByFirstname(String firstname) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findByPersonalData_Firstname(firstname,Auth.getReadIds(userAuth));
    }

    public List<User> getByLastname(String lastname) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findByPersonalData_Lastname(lastname,Auth.getReadIds(userAuth));
    }

    public List<User> getByFullName(String firstname,String lastname) {
        val userAuth=(User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findByPersonalData_FirstnameAndPersonalData_Lastname(firstname,lastname,Auth.getReadIds(userAuth));
    }



}
