package com.kakaotechcampus.journey_planner.infra.iTextPdf;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PdfGenerator {
    private final static String htmlFileSource = "templates/html/PdfTemplate3.html";

    /*
     * Make PDF with ITextPdf library
     * filename - PDF Title what you want to use
     * **/
    public void createPdf(ByteArrayOutputStream baos) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("테스트용으로 작성중입니다.", font);

        document.add(chunk);
        document.close();
    }

    public void createPlanPdf(ByteArrayOutputStream baos) throws IOException {
        ClassPathResource resource = new ClassPathResource(htmlFileSource);
        try (InputStream inputStream = resource.getInputStream()) {
            HtmlConverter.convertToPdf(inputStream, baos);
        }
    }
}
