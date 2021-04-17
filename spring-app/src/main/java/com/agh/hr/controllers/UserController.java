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

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<User> getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return HttpStatus.NO_CONTENT;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public HttpStatus insertUser(@RequestBody User user) {
        return userService.saveUser(user) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public HttpStatus updateUser(@RequestBody User user) {
        return userService.saveUser(user) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "/userByFirstname/{name}", method = RequestMethod.GET)
    public List<User> getUserByFirstname(@PathVariable String name) {
        return userService.getByFirstname(name);
    }

    @RequestMapping(value = "/userByLastname/{name}", method = RequestMethod.GET)
    public List<User> getUserByLastname(@PathVariable String name) {
        return userService.getByLastname(name);
    }

    @RequestMapping(value = "/userByFullname/{firstname}/{lastname}", method = RequestMethod.GET)
    public List<User> getUserByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return userService.getByFullName(firstname,lastname);
    }

}
