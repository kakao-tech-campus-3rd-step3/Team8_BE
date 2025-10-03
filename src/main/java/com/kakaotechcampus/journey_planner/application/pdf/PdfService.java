package com.kakaotechcampus.journey_planner.application.pdf;

import com.kakaotechcampus.journey_planner.domain.pdf.PdfGenerator;
import com.kakaotechcampus.journey_planner.infra.flyingsaucer.properties.FontProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class PdfService {
    private final PdfGenerator pdfGenerator;

    public void createPDF(OutputStream outputStream) {
        pdfGenerator.addFont(FontProperties.DEFAULT_FONT);
        pdfGenerator.createPdf(outputStream);
    }
}
