package com.epam.beans.services;

import com.epam.beans.models.Event;
import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 2/3/2016
 * Time: 11:22 AM
 */
public interface BookingService {

    double getTicketPrice(String event, String auditorium, LocalDateTime dateTime, List<Integer> seats, User user);

    Ticket bookTicket(User user, Ticket ticket);

    List<Ticket> getTicketsForEvent(String event, String auditorium, LocalDateTime date);

    List<Ticket> getAllTickets();

    List<Ticket> getTickets(Event event);

    List<Ticket> getTickets(User user);

}
