package com.epam.beans.aspects;

import com.epam.beans.aspects.mocks.CountAspectMock;
import com.epam.beans.configuration.AppConfiguration;
import com.epam.beans.configuration.db.DataSourceConfiguration;
import com.epam.beans.configuration.db.DbSessionFactory;
import com.epam.beans.daos.mocks.*;
import com.epam.beans.models.Event;
import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import com.epam.beans.services.BookingService;
import com.epam.beans.services.EventService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 13/2/16
 * Time: 7:20 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class,
                                 com.epam.beans.configuration.TestAspectsConfiguration.class})
@Transactional
@WebAppConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestCounterAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingDAOBookingMock bookingDAOBookingMock;

    @Autowired
    private EventDAOMock eventDAOMock;

    @Autowired
    private UserDAOMock userDAOMock;

    @Autowired
    private CounterAspect       counterAspect;

    @Autowired
    private UserAccountDAOMock userAccountDAOMock;

    @Autowired
    private DBAuditoriumDAOMock auditoriumDAOMock;

    @Before
    public void init() {
        CountAspectMock.cleanup();
        auditoriumDAOMock.init();
        userDAOMock.init();
        eventDAOMock.init();
        bookingDAOBookingMock.init();
        userAccountDAOMock.init();
    }

    @After
    public void cleanup() {
        CountAspectMock.cleanup();
        auditoriumDAOMock.cleanup();
        eventDAOMock.cleanup();
        bookingDAOBookingMock.cleanup();
        userAccountDAOMock.cleanup();
        userDAOMock.cleanup();

    }

    @Test
    public void testAccessedByName() {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        eventService.getByName("testName1");
        eventService.getByName("testName2");
        eventService.getByName("testName2");
        eventService.getByName("testName3");
        eventService.getByName(testEvent1.getName());
        eventService.getEvent(testEvent1.getName(), testEvent1.getAuditorium(), testEvent1.getDateTime());
        eventService.getEvent(testEvent1.getName(), testEvent1.getAuditorium(), testEvent1.getDateTime());
        eventService.getByName(testEvent1.getName());
        HashMap<String, Integer> expected = new HashMap<String, Integer>() {{
            put("testName1", 1);
            put("testName2", 2);
            put("testName3", 1);
            put(testEvent1.getName(), 4);
        }};
        assertEquals(expected, CounterAspect.getAccessByNameStat());
    }

    @Test
    public void testGetPriceByName() {
        Event event = (Event) applicationContext.getBean("testEvent1");
        User user = (User) applicationContext.getBean("testUser1");
        List<Integer> seats = Arrays.asList(1, 2, 3, 4);
        bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seats,
                                      user);
        bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seats,
                                      user);
        HashMap<String, Integer> expected = new HashMap<String, Integer>() {{
            put(event.getName(), 2);
        }};
        assertEquals(expected, CounterAspect.getGetPriceByNameStat());
    }

    @Test
    public void testBookTicketByName() {
        User user = (User) applicationContext.getBean("testUser1");
        Ticket ticket1 = (Ticket) applicationContext.getBean("testTicket1");

        bookingService.bookTicket(user, new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(5, 6), user,
                                                   ticket1.getPrice()));
        bookingService.bookTicket(user, new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(7, 8), user,
                                                   ticket1.getPrice()));
        bookingService.bookTicket(user, new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(9, 10), user,
                                                   ticket1.getPrice()));
        HashMap<String, Integer> expected = new HashMap<String, Integer>() {{
            put(ticket1.getEvent().getName(), 3);

        }};
        assertEquals(expected, CounterAspect.getBookTicketByNameStat());
    }
}
