package com.epam.beans.utils;


import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PdfTicketView extends AbstractPdfView {

    public PdfTicketView() {
        setContentType("application/pdf");
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        document.open();
        Table table = new Table(2);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            table.addCell(entry.getKey());
            table.addCell(entry.getValue().toString());
        }
        document.add(table);
        document.close();
    }
}
