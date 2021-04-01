package com.agh.hr.controllers;

import com.agh.hr.persistence.service.FooUserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agh.hr.persistence.model.FooUser;

import java.util.List;

@RestController
public class FooController {

    private final FooUserService userService;

    // spring automatically injects 'beans' based on annotations.
    // because we annotated FooUserService with @Service  and we annotated this constructor
    // with @Autowired spring will instantiate FooUserService and inject it into this constructor.
    @Autowired
    public FooController(FooUserService userService) {
        this.userService = userService;
    }

    // example
    @GetMapping("/foo-users")
    public ResponseEntity<List<FooUser>> foo() {
        val foo = FooUser.builder()
                .firstname("basia")
                .lastname("kasia").build();

        if (!this.userService.hasUserWithName("basia")) {
            this.userService.saveUser(foo);
        }

        return ResponseEntity.ok(this.userService.getAll());
    }

}
