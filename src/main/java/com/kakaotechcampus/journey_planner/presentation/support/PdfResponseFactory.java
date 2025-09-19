package com.kakaotechcampus.journey_planner.presentation.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class PdfResponseFactory {
    public static HttpHeaders getPdfHeader(String pdfName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", pdfName);
        return headers;
    }
}
