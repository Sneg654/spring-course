package com.epam.beans.daos;

import com.epam.beans.models.Event;
import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;

import java.util.List;
import java.util.Objects;


public interface BookingDAO {

    Ticket create(User user, Ticket ticket);

    void delete(User user, Ticket booking);

    List<Ticket> getTickets(Event event);

    List<Ticket> getTickets(User user);

    long countTickets(User user);

    List<Ticket> getAllTickets();

    static void validateUser(User user) {
        if (Objects.isNull(user)) {
            throw new NullPointerException("User is [null]");
        }
        if (Objects.isNull(user.getEmail())) {
            throw new NullPointerException("User email is [null]");
        }
    }

    static void validateTicket(Ticket ticket) {
        if (Objects.isNull(ticket)) {
            throw new NullPointerException("Ticket is [null]");
        }
    }
}
