package com.epam.beans.configuration;

import com.epam.beans.daos.BookingDAO;
import com.epam.beans.daos.mocks.BookingDAODiscountMock;
import com.epam.beans.services.DiscountService;
import com.epam.beans.services.DiscountServiceImpl;
import com.epam.beans.services.discount.BirthdayStrategy;
import com.epam.beans.services.discount.TicketsStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class TestStrategiesConfiguration {

    @Bean
    public BirthdayStrategy birthdayStrategy() {
        return new BirthdayStrategy(1.0, 0);
    }

    @Bean
    public TicketsStrategy ticketsStrategy() {
        return new TicketsStrategy(bookingDiscountDAO(), 0.5, 2, 0);
    }

    @Bean
    public BookingDAO bookingDiscountDAO() {
        return new BookingDAODiscountMock("Test User", 1);
    }

    @Bean
    public DiscountService discountServiceImpl() {
        return new DiscountServiceImpl(Arrays.asList(birthdayStrategy(), ticketsStrategy()));
    }
}
