package com.epam.beans.services;

import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 2/1/2016
 * Time: 7:32 PM
 */
public interface UserService {

    User register(User user);

    void remove(User user);

    User getById(long id);

    User getUserByEmail(String email);

    List<User> getUsersByName(String name);

    List<Ticket> getBookedTickets(User user);

    List<User> getAll();
}
