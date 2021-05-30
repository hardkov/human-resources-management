package com.agh.hr.persistence.service;

import com.agh.hr.config.pdf.LeavePdfConfig;
import com.agh.hr.pdfApi.PdfGenerator;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PdfService {
    private final LeaveApplicationService leaveApplicationService;

    @Autowired
    public PdfService(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    public Optional<byte[]> generatePdfForLeaveApplication(Long id) {
        return leaveApplicationService.getById(id).flatMap(
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
                            LeavePdfConfig.skeleton,
                            contentInput,
                            LocalDate.now(),
                            null,
                            signatures
                    );
                }
        );
    }
}
