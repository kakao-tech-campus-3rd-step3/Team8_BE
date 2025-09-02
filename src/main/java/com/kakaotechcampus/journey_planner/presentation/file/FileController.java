package com.kakaotechcampus.journey_planner.presentation.file;

import com.kakaotechcampus.journey_planner.application.pdf.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final PdfService pdfService;

    @GetMapping("/{download-link}")
    public ResponseEntity<?> downloadJourneyPlan(@PathVariable("download-link") String key){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfService.createPdfFile(baos);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDispositionFormData("attachment", "test.pdf");
        httpHeaders.setContentLength(baos.size());

        return new ResponseEntity<>(baos.toByteArray(), httpHeaders, HttpStatus.OK);
    }
}
