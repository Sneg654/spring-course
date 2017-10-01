package com.epam.beans.services.discount;

import com.epam.beans.models.User;


public interface DiscountStrategy {

    double calculateDiscount(User user);
}
