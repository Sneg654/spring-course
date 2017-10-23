package com.epam.beans.utils;

import com.epam.beans.models.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationPdfHttpMessageConverter extends AbstractHttpMessageConverter<List<Ticket>> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    public ApplicationPdfHttpMessageConverter() {
        super(new MediaType("application", "pdf"));
    }

    @Override
    public boolean canRead(Class aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class aClass, MediaType mediaType) {
        MediaType pdfType = new MediaType("application", "pdf");
        return mediaType.equals(pdfType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(new MediaType("application", "pdf"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return List.class.equals(clazz);
    }

    @Override
    protected List<Ticket> readInternal(Class<? extends List<Ticket>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("this operation is not implemented");
    }

    @Override
    protected void writeInternal(List<Ticket> tickets, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Document document = new Document();
        PdfWriter pdfWriter = null;
        PdfTicketView pdfTicketView = new PdfTicketView();
        Map<String, Object> stringTicketMap = new HashMap<>();
        tickets.forEach(ticket ->stringTicketMap.put(ticket.getEvent().getName(), ticket));
        try {
            pdfWriter = PdfWriter.getInstance(document, outputMessage.getBody());
            pdfTicketView.buildPdfDocument(stringTicketMap,document, pdfWriter, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
