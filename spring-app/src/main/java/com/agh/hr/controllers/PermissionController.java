package com.agh.hr.controllers;

import com.agh.hr.persistence.DTO.Converters;
import com.agh.hr.persistence.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PermissionController {

    private final PermissionService permissionService;
    private final Converters converters;

    @Autowired
    public PermissionController( PermissionService permissionService,Converters converters) {
        this.converters = converters;
        this.permissionService = permissionService;
    }


}
