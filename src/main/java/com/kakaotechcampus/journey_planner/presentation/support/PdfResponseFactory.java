package com.kakaotechcampus.journey_planner.presentation.support;

import com.kakaotechcampus.journey_planner.application.pdf.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Component
@RequiredArgsConstructor
public class PdfResponseFactory {
    private final PdfService pdfService;

    public StreamingResponseBody linkToPdfFile() {
        return pdfService::createPDF;
    }

    public HttpHeaders getPdfHeader(String pdfName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", pdfName);
        return headers;
    }
}
