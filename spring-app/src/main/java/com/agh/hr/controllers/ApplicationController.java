package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
