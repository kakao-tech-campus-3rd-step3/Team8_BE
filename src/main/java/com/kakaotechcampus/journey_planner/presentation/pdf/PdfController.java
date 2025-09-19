package com.kakaotechcampus.journey_planner.presentation.pdf;

import com.kakaotechcampus.journey_planner.presentation.support.PdfResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class PdfController {
    private final PdfResponseFactory pdfResponseFactory;

    @GetMapping
    public ResponseEntity<StreamingResponseBody> downloadJourneyPlan() {
        return ResponseEntity.ok()
                .headers(pdfResponseFactory.getPdfHeader(""))
                .body(pdfResponseFactory.linkToPdfFile());
    }
}
