package com.agh.hr.persistence.service;

import com.agh.hr.config.pdf.BonusPdfConfig;
import com.agh.hr.config.pdf.LeavePdfConfig;
import com.agh.hr.pdfApi.PdfGenerator;
import com.agh.hr.persistence.service.application.ApplicationService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PdfService {
    private final ApplicationService applicationService;

    @Autowired
    public PdfService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public Optional<byte[]> generatePdfForLeaveApplication(Long id) {
        return applicationService.getLeaveApplicationById(id).flatMap(
                leave -> {
                    val contentInput = new ArrayList<String>();
                    contentInput.add(leave.getUser().getPersonalData().getFirstname());
                    contentInput.add(leave.getUser().getPersonalData().getLastname());
                    contentInput.add(leave.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    contentInput.add(leave.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));

                    val signatures = new ArrayList<String>();
                    signatures.add("Podpis wnioskującego");
                    return PdfGenerator.toPdf(
                            leave.getUser(),
                            null,
                            LeavePdfConfig.title,
                            LeavePdfConfig.skeleton + "\n" + leave.getContent(),
                            contentInput,
                            LocalDate.now(),
                            leave.getPlace(),
                            signatures
                    );
                }
        );
    }

    public Optional<byte[]> generatePdfForBonusApplication(Long id) {
        return applicationService.getBonusApplicationById(id).flatMap(
                bonus -> {
                    val contentInput = new ArrayList<String>();
                    contentInput.add(bonus.getUser().getPersonalData().getFirstname());
                    contentInput.add(bonus.getUser().getPersonalData().getLastname());
                    contentInput.add(String.format("%.2f", bonus.getMoney().floatValue()));
                    contentInput.add("PLN");

                    val signatures = new ArrayList<String>();
                    signatures.add("Podpis wnioskującego");
                    return PdfGenerator.toPdf(
                            bonus.getUser(),
                            null,
                            BonusPdfConfig.title,
                            BonusPdfConfig.skeleton + "\n" + bonus.getContent(),
                            contentInput,
                            LocalDate.now(),
                            bonus.getPlace(),
                            signatures
                    );
                }
        );
    }

    public Optional<byte[]> generatePdfForDelegationApplication(Long id) {
        return applicationService.getDelegationApplicationById(id).flatMap(
                delegation -> {
                    val contentInput = new ArrayList<String>();
                    contentInput.add(delegation.getUser().getPersonalData().getFirstname());
                    contentInput.add(delegation.getUser().getPersonalData().getLastname());
                    contentInput.add(delegation.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    contentInput.add(delegation.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    contentInput.add(delegation.getDestination());

                    val signatures = new ArrayList<String>();
                    signatures.add("Podpis wnioskującego");
                    return PdfGenerator.toPdf(
                            delegation.getUser(),
                            null,
                            LeavePdfConfig.title,
                            LeavePdfConfig.skeleton + "\n" + delegation.getContent(),
                            contentInput,
                            LocalDate.now(),
                            delegation.getPlace(),
                            signatures
                    );
                }
        );
    }
}
