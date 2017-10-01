package com.epam.beans.services;

import com.epam.beans.models.Event;
import com.epam.beans.models.User;


public interface DiscountService {

    double getDiscount(User user, Event event);
}
