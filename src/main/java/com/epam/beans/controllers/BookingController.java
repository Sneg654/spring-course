package com.epam.beans.controllers;

import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import com.epam.beans.services.BookingService;
import com.epam.beans.services.EventService;
import com.epam.beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventServiceImpl;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    @Qualifier("bookingServiceImpl")
    private BookingService bookingServiceImpl;


    /**
     * List of tickets to get data from Database
     */
    private List<Ticket> ticketList = new ArrayList<>();

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public String getAllTickets(ModelMap model) {
        ticketList = bookingServiceImpl.getAllTickets();
        model.addAttribute("ticketList", ticketList);
        return "tickets";
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public String bookTicket(Ticket ticket, ModelMap model) {
        User user = userServiceImpl.getById(ticket.getUser().getId());
        if (!ticketList.isEmpty()) ticketList.clear();
        Ticket bookedTicket = bookingServiceImpl.bookTicket(user, ticket);
        bookedTicket.setEvent(eventServiceImpl.getById(ticket.getEvent().getId()));
        bookedTicket.setUser(userServiceImpl.getById(ticket.getUser().getId()));
        ticketList.add(bookedTicket);
        model.addAttribute("ticketList", ticketList);
        return "booked-tickets";
    }


}
