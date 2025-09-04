package com.kakaotechcampus.journey_planner.application.pdf;

import com.itextpdf.text.DocumentException;
import com.kakaotechcampus.journey_planner.global.exception.CustomRuntimeException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import com.kakaotechcampus.journey_planner.infra.iTextPdf.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PdfService {
    private final PdfGenerator pdfGenerator;

    public void createPdfFile(ByteArrayOutputStream baos){
        try{
            pdfGenerator.createPdf(baos);
        }catch (FileNotFoundException | DocumentException e){
            throw new CustomRuntimeException(ErrorCode.NO_FILE);
        }
    }

    public void createHtmlPdfFile(ByteArrayOutputStream baos){
        try{
            pdfGenerator.createPlanPdf(baos);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
