package com.epam.beans.controllers.rest;

import com.epam.beans.models.Event;
import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import com.epam.beans.services.BookingService;
import com.epam.beans.services.EventService;
import com.epam.beans.services.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/tickets")
public class BookingRestController {

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventServiceImpl;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    @Qualifier("bookingServiceImpl")
    private BookingService bookingServiceImpl;


    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buy(@RequestParam long eventId,
                              @RequestParam String userMail,
                              @RequestParam String airDate,
                              @RequestParam String airTime,
                              @RequestParam String seats) throws IOException {
        User user = userMail != null ? userServiceImpl.getUserByEmail(userMail) : null;
        Event event = eventServiceImpl.getById(eventId);
        LocalDateTime airDateTime = LocalDateTime.of(LocalDate.parse(airDate), LocalTime.parse(airTime));
        List<Integer> seatList = parseSeats(seats);
        Ticket ticket = new Ticket(event, airDateTime, seatList, user, 0.0);
        bookingServiceImpl.bookTicket(user, ticket);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(ticket);
    }



    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public String getPrice(@RequestParam String eventId,
                           @RequestParam String userMail,
                           @RequestParam String airDate,
                           @RequestParam String airTime,
                           @RequestParam String seats) throws IOException {
        User user = userMail != null ? userServiceImpl.getUserByEmail(userMail) : null;
        Event event = eventServiceImpl.getById(Long.valueOf(eventId));
        LocalDateTime airDateTime = LocalDateTime.of(LocalDate.parse(airDate), LocalTime.parse(airTime));
        List<Integer> seatList = parseSeats(seats);
        Double price =  bookingServiceImpl.getTicketPrice(event.getName(), event.getAuditorium().getName(), airDateTime, seatList, user);
        return price.toString();
    }

    @RequestMapping(value = "/ticketsPDF", headers = "Accept=application/pdf", method = RequestMethod.GET)
    public List<Ticket> getAllTickets() throws Exception {
        return bookingServiceImpl.getAllTickets();
    }

    private List<Integer> parseSeats(String seats) {
        if (seats == null) throw new IllegalArgumentException("Seats cannot be null");
        seats = seats.replace(" ", "");
        List<Integer> seatList = new ArrayList<>();
        if (seats.contains(",")) {
            for (String s : seats.split(",")) {
                seatList.add(Integer.valueOf(s));
            }
        } else {
            seatList.add(Integer.valueOf(seats));
        }
        return seatList;
    }
}
