package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long UserId) {
            userRepository.deleteById(UserId);
    }

    @Transactional
    public List<User> getByFirstname(String firstname) {
        return userRepository.findByPersonalData_Firstname(firstname);
    }

    @Transactional
    public List<User> getByLastname(String lastname) {
        return userRepository.findByPersonalData_Lastname(lastname);
    }

    @Transactional
    public List<User> getByFullName(String firstname,String lastname) {
        return userRepository.findByPersonalData_FirstnameAndPersonalData_Lastname(firstname,lastname);
    }



}
