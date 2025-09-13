package com.kakaotechcampus.journey_planner.application.pdf;

import com.kakaotechcampus.journey_planner.domain.pdf.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class PdfService {
    public final PdfGenerator pdfGenerator;

    public void createPDF(OutputStream outputStream) {
        pdfGenerator.createPdf(outputStream);
    }
}
