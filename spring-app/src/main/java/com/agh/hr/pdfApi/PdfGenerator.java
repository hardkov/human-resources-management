package com.agh.hr.pdfApi;

import com.agh.hr.persistence.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import lombok.val;
import lombok.var;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.print.Doc;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PdfGenerator {
    private static void tryDocumentAdd(Document document, Paragraph paragraph) {
        try {
            document.add(paragraph);
        } catch (Exception ignored) {}
    }

    private static Optional<Paragraph> dateLocationGenerate(LocalDate date, String documentLocation, Font font) {
        if(date != null || (documentLocation != null && !documentLocation.isEmpty())){
            Paragraph dateLocPar = new Paragraph(null, font);
            dateLocPar.setAlignment(Element.ALIGN_RIGHT);
            if(date != null && (documentLocation != null && !documentLocation.isEmpty())){
                dateLocPar.add(String.format("%s, %s", documentLocation, date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            } else if (date != null) {
                dateLocPar.add(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            } else {
                dateLocPar.add(documentLocation);
            }
            return Optional.of(dateLocPar);
        }
        else
            return Optional.empty();
    }

    private static Optional<Paragraph> employeeDataGenerate(User user, Font font) {
        if(user != null) {
            val content = String.format(
                    "%s %s\n%s\n%s",
                    user.getPersonalData().getFirstname(),
                    user.getPersonalData().getLastname(),
                    user.getPosition(),
                    user.getPersonalData().getAddress()
            );
            Paragraph pretitlePar = new Paragraph(content, font);
            pretitlePar.setAlignment(Element.ALIGN_LEFT);
            return Optional.of(pretitlePar);
        }
        else
            return Optional.empty();
    }

    private static Optional<Paragraph> pretitleGenerate(String pretitle, Font font) {
        if(pretitle != null && !pretitle.isEmpty()) {
            Paragraph pretitlePar = new Paragraph(pretitle, font);
            pretitlePar.setAlignment(Element.ALIGN_RIGHT);
            return Optional.of(pretitlePar);
        }
        else
            return Optional.empty();
    }

    private static Optional<Paragraph> titleGenerate(String title, Font font) {
        if(title != null && !title.isEmpty()) {
            Paragraph titlePar = new Paragraph(title, font);
            titlePar.setSpacingBefore(20.0f);
            titlePar.setSpacingAfter(15.0f);
            titlePar.setAlignment(Element.ALIGN_CENTER);
            return Optional.of(titlePar);
        }
        else
            return Optional.empty();
    }

    private static List<Paragraph> contentGenerate(String contentSkeleton, List<String> contentInput, Font font, Font font_bold) {
        val paragraphs = new ArrayList<Paragraph>();
        for (var paragraphBody : contentSkeleton.split("\n")) {
            val paragraph = new Paragraph(null, font);
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
            paragraph.add(Chunk.TABBING);
            for (var textChunk : paragraphBody.split("((?<=%s)|(?=%s))")) {
                if (textChunk.equals("%s")) {
                    if (contentInput.isEmpty())
                        return new ArrayList<>();
                    paragraph.add(new Phrase(contentInput.get(0), font_bold));
                    contentInput.remove(0);
                } else
                    paragraph.add(textChunk);
            }
            paragraphs.add(paragraph);
        }
        return paragraphs;
    }

    private static List<Paragraph> generateSignatures(Document document, List<String> signatures, Font font) {
        val paragraphs = new ArrayList<Paragraph>();
        for (var signature: signatures) {
            val signaturePar = new Paragraph(null, font);
            signaturePar.setSpacingBefore(20.f);
            signaturePar.setAlignment(Element.ALIGN_CENTER);

            val lineLen = document.right(document.rightMargin()) - document.leftMargin();
            val signatureRelativeLength = 0.4f;
            signaturePar.setIndentationLeft(lineLen*(1-signatureRelativeLength));
            val tabSet = new TabSettings();
            val tabStops = new ArrayList<TabStop>();
            tabStops.add(new TabStop(lineLen*signatureRelativeLength, new DottedLineSeparator()));
            tabSet.setTabStops(tabStops);
            signaturePar.setTabSettings(tabSet);

            signaturePar.add(Chunk.TABBING);
            signaturePar.add("\n" + signature);

            paragraphs.add(signaturePar);
        }
        return paragraphs;
    }

    public static Optional<byte[]> toPdf(
            User employee,
            String pretitle,
            String title,
            String contentSkeleton,
            List<String> contentInput,
            LocalDate date,
            String documentLocation,
            List<String> signatures) {
        try {
            Document document = new Document();

            Font font = FontFactory.getFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250);
            font.setSize(12);

            Font font_bold = FontFactory.getFont(BaseFont.TIMES_BOLD, BaseFont.CP1250);
            font_bold.setSize(12);

            Font font_title = FontFactory.getFont(BaseFont.TIMES_BOLD, BaseFont.CP1250);
            font_title.setSize(18);

            ByteArrayOutputStream output = new ByteArrayOutputStream(1024 * 1024);
            PdfWriter.getInstance(document, output);

            document.setPageSize(PageSize.A4);
            document.setMargins(72, 72, 108, 108);

            document.open();


            dateLocationGenerate(date, documentLocation, font).ifPresent(p ->
                    tryDocumentAdd(document, p)
            );

            employeeDataGenerate(employee, font).ifPresent(p ->
                    tryDocumentAdd(document, p)
            );

            pretitleGenerate(pretitle, font).ifPresent(p ->
                    tryDocumentAdd(document, p)
            );

            titleGenerate(title, font_title).ifPresent(p ->
                    tryDocumentAdd(document, p)
            );

            contentGenerate(contentSkeleton, contentInput, font, font_bold).forEach(p ->
                    tryDocumentAdd(document, p)
            );

            generateSignatures(document, signatures, font).forEach(p ->
                    tryDocumentAdd(document, p)
            );

            document.close();
            return Optional.of(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static ResponseEntity<Resource> bytesToHttpResponse(byte[] bytes, String filename) {
        val resource = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "multipart/form-data")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
