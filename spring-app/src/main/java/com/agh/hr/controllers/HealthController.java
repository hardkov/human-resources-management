package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.persistence.dto.Converters;
import com.agh.hr.persistence.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;

@RestController
public class HealthController implements SecuredRestController {

    @RequestMapping(value = "/health-check", method = RequestMethod.GET)
    public ResponseEntity<Void> getRoot() throws IOException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/public/pdf-test")
    public ResponseEntity<Resource> getPdf() {
        val inputs = new LinkedList<String>();
        inputs.add("Imię");
        inputs.add("Nazwisko");
        return PdfGenerator.toPdf(
                null,
                "test",
                "test",
                "Testowanie testowego generatora pdf. %s %s testuje testowy generator i udostępnia stworzony pdf przez publiczny endpoint.\nDrugi akapit.",
                inputs,
                LocalDate.now(),
                "Test Zdrój",
                Collections.singletonList("Podpis testowy")
        ).map(pdfFile -> PdfGenerator.bytesToHttpResponse(pdfFile,"test.pdf"))
        .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
