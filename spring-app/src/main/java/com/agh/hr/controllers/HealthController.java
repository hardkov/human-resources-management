package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HealthController implements SecuredRestController {

    @RequestMapping(value = "/health-check", method = RequestMethod.GET)
    public ResponseEntity<Void> getRoot() throws IOException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
