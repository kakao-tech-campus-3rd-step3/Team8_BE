package com.kakaotechcampus.journey_planner.application.pdf.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class PdfUtils {
    /*
    * Make PDF with ITextPdf library
    * filename - PDF Title what you want to use
    * **/
    public void CreatePdf(ByteArrayOutputStream baos) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("테스트용으로 작성중입니다.", font);

        document.add(chunk);
        document.close();
    }

    /*
    * Make PDF with PDFBox library
    * filename - PDF Title what you want to use
    * **/
    public void CreatePdfWithPdfBox(String filename){
        try(PDDocument document = new PDDocument()){
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
