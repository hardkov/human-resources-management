package com.agh.hr.controllers;

import com.agh.hr.config.security.SecuredRestController;
import com.agh.hr.pdfApi.PdfGenerator;
import com.agh.hr.persistence.service.PdfService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pdf")
public class PdfController implements SecuredRestController {

    private final PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping(value = "/leave-application/{id}")
    public ResponseEntity<Resource> getLeaveApplicationPdf(@PathVariable Long id) {
        return pdfService.generatePdfForLeaveApplication(id)
                .map(bytes -> PdfGenerator.bytesToHttpResponse(bytes, "leave-application-" + id.toString() + ".pdf"))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/bonus-application/{id}")
    public ResponseEntity<Resource> getBonusApplicationPdf(@PathVariable Long id) {
        return pdfService.generatePdfForBonusApplication(id)
                .map(bytes -> PdfGenerator.bytesToHttpResponse(bytes, "bonus-application-" + id.toString() + ".pdf"))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/delegation-application/{id}")
    public ResponseEntity<Resource> getDelegationApplicationPdf(@PathVariable Long id) {
        return pdfService.generatePdfForDelegationApplication(id)
                .map(bytes -> PdfGenerator.bytesToHttpResponse(bytes, "delegation-application-" + id.toString() + ".pdf"))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
