package com.epam.beans.services.discount;

import com.epam.beans.daos.mocks.BookingDAODiscountMock;
import com.epam.beans.models.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.epam.beans.configuration.TestStrategiesConfiguration.class)
public class TicketsStrategyTest {

    @Autowired
    private TicketsStrategy strategy;

    @Autowired
    private BookingDAODiscountMock bookingDAODiscountMock;

    @org.junit.Test
    public void testCalculateDiscount_UserHasDiscount() throws Exception {
        System.out.println(strategy.getClass());
        User userWithDiscount = new User("test@ema.il", bookingDAODiscountMock.userThatBookedTickets, LocalDate.now());
        double discount = strategy.calculateDiscount(userWithDiscount);
        assertEquals("User: [" + userWithDiscount + "] has tickets discount", strategy.ticketsDiscountValue, discount, 0.00001);
    }

    @org.junit.Test
    public void testCalculateDiscount_UserHasNoDiscount() throws Exception {
        User userWithoutDiscount = new User("test@ema.il", "Test Name 2", LocalDate.now().minus(1, ChronoUnit.DAYS));
        double discount = strategy.calculateDiscount(userWithoutDiscount);
        assertEquals("User: [" + userWithoutDiscount + "] doesn't have tickets discount", strategy.defaultDiscount, discount, 0.00001);
    }
}
