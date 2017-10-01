package com.epam.beans.services;

import com.epam.beans.models.Event;
import com.epam.beans.models.User;
import com.epam.beans.services.discount.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    public static final double MAX_DISCOUNT = 0.8;

    private final List<DiscountStrategy> strategies;

    @Autowired
    public DiscountServiceImpl(@Value("#{strategies}") List<DiscountStrategy> strategies) {
        this.strategies = Collections.unmodifiableList(strategies);
    }

    @Override
    public double getDiscount(User user, Event event) {
        final Double discount = strategies.stream().map(strategy -> strategy.calculateDiscount(user)).reduce(
                (a, b) -> a + b).orElse(0.0);
        return Double.compare(discount, MAX_DISCOUNT) > 0 ? MAX_DISCOUNT : discount;
    }
}
