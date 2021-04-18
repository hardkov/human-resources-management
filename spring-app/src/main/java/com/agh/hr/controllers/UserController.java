package com.agh.hr.controllers;

import com.agh.hr.persistence.model.User;
import com.agh.hr.persistence.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public  ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> insertUser(@RequestBody User user) {
        return userService.saveUser(user)?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        return userService.saveUser(user)?ResponseEntity.accepted().build():ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/userByFirstname/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByFirstname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByFirstname(name));
    }

    @RequestMapping(value = "/userByLastname/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByLastname(@PathVariable String name) {
        return ResponseEntity.ok(userService.getByLastname(name));
    }

    @RequestMapping(value = "/userByFullname/{firstname}/{lastname}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserByFullName(@PathVariable String firstname,@PathVariable String lastname) {
        return ResponseEntity.ok(userService.getByFullName(firstname,lastname));
    }

}
