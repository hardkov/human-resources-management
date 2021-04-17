package com.agh.hr.controllers;

import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agh.hr.persistence.model.FooUser;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // spring automatically injects 'beans' based on annotations.
    // because we annotated FooUserService with @Service  and we annotated this constructor
    // with @Autowired spring will instantiate FooUserService and inject it into this constructor.
    //@Autowired
   // public UserController(UserService userService) {
    //    this.userService = userService;
    //}


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<User> getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }


}
