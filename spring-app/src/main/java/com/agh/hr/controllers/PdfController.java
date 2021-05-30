package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.pdfApi.PdfGenerator;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.service.ApplicationService;
import com.agh.hr.persistence.service.PdfService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

@RestController
public class PdfController implements SecuredRestController {

    private final PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping(value = "/api/pdf/leave-application/{id}")
    public ResponseEntity<Resource> getPdf(@PathVariable Long id) {
        return pdfService.generatePdfForLeaveApplication(id)
                .map(bytes -> PdfGenerator.bytesToHttpResponse(bytes, "leave-application-" + id.toString() + ".pdf"))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
