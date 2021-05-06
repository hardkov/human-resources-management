package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApplicationController implements SecuredRestController {

    private final ApplicationService applicationService;
    private final Converters converters;

    @Autowired
    public ApplicationController( ApplicationService applicationService,Converters converters) {
        this.converters = converters;
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/health-check", method = RequestMethod.GET)
    public ResponseEntity<Void> getRoot() throws IOException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
