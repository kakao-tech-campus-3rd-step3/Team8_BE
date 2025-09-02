package com.kakaotechcampus.journey_planner.application.pdf;

import com.itextpdf.text.DocumentException;
import com.kakaotechcampus.journey_planner.application.pdf.utils.PdfUtils;
import com.kakaotechcampus.journey_planner.global.exception.CustomRuntimeException;
import com.kakaotechcampus.journey_planner.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class PdfService {
    private final PdfUtils pdfUtils;

    public void createPdfFile(ByteArrayOutputStream baos){
        try{
            pdfUtils.CreatePdf(baos);
        }catch (FileNotFoundException | DocumentException e){
            throw new CustomRuntimeException(ErrorCode.NO_FILE);
        }
    }
}
