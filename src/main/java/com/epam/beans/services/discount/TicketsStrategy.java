package com.epam.beans.services.discount;

import com.epam.beans.daos.BookingDAO;
import com.epam.beans.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("ticketsStrategy")
@PropertySource({"classpath:strategies/strategies.properties"})
@Transactional
public class TicketsStrategy implements DiscountStrategy {

    public final BookingDAO bookingDAO;
    public final double     ticketsDiscountValue;
    public final int        discountThreshold;
    public final double     defaultDiscount;

    @Autowired
    public TicketsStrategy(@Qualifier("bookingDAO") BookingDAO bookingDAO,
                           @Value("${tickets.discount}") double ticketsDiscountValue,
                           @Value("${tickets.discount.threshold}") int discountThreshold,
                           @Value("${tickets.discount.default}") double defaultDiscount) {
        this.bookingDAO = bookingDAO;
        this.ticketsDiscountValue = ticketsDiscountValue;
        this.discountThreshold = discountThreshold;
        this.defaultDiscount = defaultDiscount;
    }

    @Override
    public double calculateDiscount(User user) {
        final long boughtTicketsCount = bookingDAO.countTickets(user);
        if ((boughtTicketsCount + 1) % discountThreshold == 0) {
            return ticketsDiscountValue;
        }
        return defaultDiscount;
    }
}