package com.agh.hr.persistence.service;

import com.agh.hr.persistence.model.FooUser;
import com.agh.hr.persistence.repository.FooUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
// it should provide high-level api aka abstraction over repository if needed
public class FooUserService {

    private final FooUserRepository userRepository;

    @Autowired
    public FooUserService(FooUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // todo in real user service create a method that finds user by username -> whatever that is?
    public Optional<FooUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(FooUser user) {
        this.userRepository.save(user);
    }

    public boolean hasUserWithName(String firstname) {
        return this.userRepository.findByUsername(firstname).isPresent();
    }

    public List<FooUser> getAll() {
        return this.userRepository.findAll();
    }

}
