package com.epam.beans.aspects.mocks;

import com.epam.beans.aspects.DiscountAspect;
import com.epam.beans.services.discount.BirthdayStrategy;
import com.epam.beans.services.discount.TicketsStrategy;

import java.util.HashMap;

public class DiscountAspectMock extends DiscountAspect {
    public static void cleanup() {
        discountPerUserCounter.put(BirthdayStrategy.class.getSimpleName(), new HashMap<>());
        discountPerUserCounter.put(TicketsStrategy.class.getSimpleName(), new HashMap<>());
    }
}
