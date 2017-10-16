package com.epam.beans.services;

import com.epam.beans.configuration.AppConfiguration;
import com.epam.beans.configuration.TestBookingServiceConfiguration;
import com.epam.beans.configuration.db.DataSourceConfiguration;
import com.epam.beans.configuration.db.DbSessionFactory;
import com.epam.beans.daos.mocks.*;
import com.epam.beans.models.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class,
                                 TestBookingServiceConfiguration.class})
@Transactional
@EnableAspectJAutoProxy(proxyTargetClass = true)
@WebAppConfiguration

public class BookingServiceImplTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("#{bookingServiceImpl}")
    private BookingService bookingService;

    @Autowired
    private BookingDAOBookingMock bookingDAOBookingMock;
    @Autowired
    private EventDAOMock          eventDAOMock;
    @Autowired
    private UserDAOMock           userDAOMock;
    @Autowired
    private DBAuditoriumDAOMock   auditoriumDAOMock;

    @Autowired
    private UserAccountDAOMock userAccountDAOMock;

    @Before
    public void init() {
        auditoriumDAOMock.init();
        userDAOMock.init();
        eventDAOMock.init();
        bookingDAOBookingMock.init();
        userAccountDAOMock.init();
    }

    @After
    public void cleanup() {
        userAccountDAOMock.cleanup();
        auditoriumDAOMock.cleanup();
        userDAOMock.cleanup();
        eventDAOMock.cleanup();
        bookingDAOBookingMock.cleanup();
    }

    @Test
    public void testGetTicketsForEvent() throws Exception {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
        List<Ticket> ticketsForEvent = bookingService.getTicketsForEvent(testEvent1.getName(),
                                                                         testEvent1.getAuditorium().getName(),
                                                                         testEvent1.getDateTime());
        assertEquals("Tickets should match", Arrays.asList(ticket), ticketsForEvent);
    }

    @Test(expected = RuntimeException.class)
    public void testBookTicket_NotRegistered() throws Exception {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        List<Ticket> before = bookingService.getTicketsForEvent(testEvent1.getName(),
                                                                testEvent1.getAuditorium().getName(),
                                                                testEvent1.getDateTime());
        User newUser = new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), LocalDate.now());
        Ticket newTicket = new Ticket(testEvent1, LocalDateTime.now(), Arrays.asList(3, 4), newUser, 0.0);
        bookingService.bookTicket(newUser, newTicket);
    }

    @Test(expected = RuntimeException.class)
    public void testBookTicket_AlreadyBooked() throws Exception {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        User testUser2 = (User) applicationContext.getBean("testUser2");
        Ticket newTicket = new Ticket(testEvent1, LocalDateTime.now(), Arrays.asList(3, 4), testUser2, 0.0);
        bookingService.bookTicket(testUser2, newTicket);
    }

    @Test
    public void testBookTicket() throws Exception {
        User testUser1 = (User) applicationContext.getBean("testUser1");
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        Ticket newTicket = new Ticket(testEvent1, testEvent1.getDateTime(), Arrays.asList(5, 6), testUser1, 0.0);
        bookingService.bookTicket(testUser1, newTicket);
        List<Ticket> ticketList = bookingService.getAllTickets();
        assertTrue("Ticket should be contained in list", ticketList.contains(newTicket));
    }

    @Test
    public void testGetTicketPrice() throws Exception {
        Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                                                           event.getDateTime(), ticket.getSeatsList(),
                                                           ticket.getUser());
        Assert.assertEquals("Price is wrong", 297.6, ticketPrice, 0.00001);
    }

    @Test
    public void testGetTicketPrice_WithoutDiscount() throws Exception {
        Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
        User user = userDAOMock.create(new User(-1, "dadsada", "asdasda", "ss", "123456", UserRole.REGISTERED_USER,LocalDate.now().minus(1, ChronoUnit.DAYS)));
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                                                           event.getDateTime(), ticket.getSeatsList(), user);
        Assert.assertEquals("Price is wrong", 595.2, ticketPrice, 0.00001);
    }

    @Test
    public void testGetTicketPrice_DiscountsForTicketsAndForBirthday() throws Exception {
        Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
        User testUser = new User(99, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UserRole.REGISTERED_USER, LocalDate.now());
        User registeredUser = userDAOMock.create(testUser);
        UserAccount userAccount = new UserAccount();
        userAccount.setId(99);
        userAccount.setUser(registeredUser);
        userAccount.setPrepaidUserMoney(5000);
        userAccountDAOMock.refillAccount(userAccount);

        bookingService.bookTicket(registeredUser,
                                  new Ticket(ticket.getEvent(), ticket.getDateTime(), Collections.singletonList(1),
                                             registeredUser, 0.0));
        bookingService.bookTicket(registeredUser,
                                  new Ticket(ticket.getEvent(), ticket.getDateTime(), Collections.singletonList(2),
                                             registeredUser, 0.0));
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                                                           event.getDateTime(), Arrays.asList(5, 6, 7, 8),
                                                           registeredUser);
        Assert.assertEquals("Price is wrong", 260.4, ticketPrice, 0.00001);
    }

    @Test
    public void testGetTicketPrice_DiscountsForTicketsAndForBirthday_MidRate() throws Exception {
        Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
        User testUser = new User(99, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UserRole.REGISTERED_USER, LocalDate.now());
        User registeredUser = userDAOMock.create(testUser);
        UserAccount userAccount = new UserAccount();
        userAccount.setId(99);
        userAccount.setUser(registeredUser);
        userAccount.setPrepaidUserMoney(5000);
        userAccountDAOMock.refillAccount(userAccount);

        bookingService.bookTicket(registeredUser,
                new Ticket(ticket.getEvent(), ticket.getDateTime(), Collections.singletonList(1),
                        registeredUser, 0.0));
        bookingService.bookTicket(registeredUser,
                new Ticket(ticket.getEvent(), ticket.getDateTime(), Collections.singletonList(2),
                        registeredUser, 0.0));
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                                                           event.getDateTime(), Arrays.asList(5, 6, 7), registeredUser);
        Assert.assertEquals("Price is wrong", 208.31999999999996, ticketPrice, 0.00001);
    }
}
