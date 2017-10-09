package com.epam.beans.controllers;

import com.epam.beans.models.Event;
import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import com.epam.beans.services.BookingService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PdfController {


    @Autowired
    @Qualifier("bookingServiceImpl")
    private BookingService bookingServiceImpl;

    /**
     * List of tickets to get data from Database
     */
    private List<Ticket> ticketList = new ArrayList<>();


    @RequestMapping(value = "/getTicketsPdfByUser", method = RequestMethod.GET, headers = "Accept=application/pdf")
    public void saveEventTicketsPdf(HttpServletResponse response, User user) throws DocumentException, IOException {
        ticketList = bookingServiceImpl.getTickets(user);
        Document document = new Document(PageSize.A4);
        document.addAuthor(user.getName());
        document.addTitle("Tickets by user");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph(String.format(
                "Tickets from given user are [%s]", ticketList)));
        document.close();
        response = setResponseParameters(response, baos);

        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();

    }

    @RequestMapping(value = "/getTicketsPdfByEvent", method = RequestMethod.GET, headers = "Accept=application/pdf")
    public void saveUserTicketsPdf(HttpServletResponse response, Event event) throws DocumentException, IOException {
        ticketList = bookingServiceImpl.getTickets(event);
        Document document = new Document(PageSize.A4);
        document.addAuthor(event.getName());
        document.addTitle("Tickets by event");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph(String.format(
                "Tickets from given event are [%s]", ticketList)));
        document.close();
        response = setResponseParameters(response, baos);

        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }

    private HttpServletResponse setResponseParameters(HttpServletResponse response, ByteArrayOutputStream baos) {
        // setting  response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.addHeader("Content-Type", "application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=\"TicketsPDF.pdf\"");
        // the content length
        response.setContentLength(baos.size());
        return  response;
    }
}
