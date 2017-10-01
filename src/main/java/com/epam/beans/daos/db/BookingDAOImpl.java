package com.epam.beans.daos.db;

import com.epam.beans.daos.AbstractDAO;
import com.epam.beans.daos.BookingDAO;
import com.epam.beans.models.Booking;
import com.epam.beans.models.Event;
import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookingDAO")
public class BookingDAOImpl extends AbstractDAO implements BookingDAO {

    @Override
    public Ticket create(User user, Ticket ticket) {
        BookingDAO.validateTicket(ticket);
        BookingDAO.validateUser(user);

        Long ticketId = (Long) getCurrentSession().save(ticket);
        Ticket storedTicket = ticket.withId(ticketId);
        Booking booking = new Booking(user, storedTicket);
        getCurrentSession().save(booking);
        return storedTicket;
    }

    @Override
    public void delete(User user, Ticket ticket) {
        Query query = getCurrentSession().createQuery(
                "delete from Booking b where b.user = :user and b.ticket = :ticket");
        query.setParameter("user", user);
        query.setParameter("ticket", ticket);
        query.executeUpdate();
        getCurrentSession().delete(ticket);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> getTickets(Event event) {
        Query query = getCurrentSession().createQuery("select b.ticket from Booking b where b.ticket.event = :event");
        query.setParameter("event", event);
        return ((List<Ticket>) query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> getTickets(User user) {
        Query query = getCurrentSession().createQuery("select b.ticket from Booking b where b.user = :user");
        query.setParameter("user", user);
        return ((List<Ticket>) query.list());
    }

    @Override
    public long countTickets(User user) {
        Query query = getCurrentSession().createQuery("select count(*) from Booking b where b.user = :user");
        query.setParameter("user", user);
        return (Long) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> getAllTickets() {
        Query query = getCurrentSession().createQuery("select b.ticket from Booking b");
        return ((List<Ticket>) query.list());
    }
}
