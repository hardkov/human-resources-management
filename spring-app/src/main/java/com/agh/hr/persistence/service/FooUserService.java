package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.FooUser;
import com.agh.hr.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// it should provide high-level api aka abstraction over repository if needed
public class FooUserService {

    private final UserRepository userRepository;

    @Autowired
    public FooUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(FooUser user) {
        this.userRepository.save(user);
    }

    public boolean hasUserWithName(String firstname) {
        return this.userRepository.findByFirstname(firstname).isPresent();
    }

    public List<FooUser> getAll() {
        return this.userRepository.findAll();
    }

}
