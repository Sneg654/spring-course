package com.epam.beans.daos;

import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;

import java.util.List;
import java.util.Objects;

public interface UserDAO {

    User create(User user);

    void delete(User user);

    User get(long id);

    User getByEmail(String email);

    User getByLogin(String login);

    List<User> getAllByName(String name);

    List<User> getAll();

    List<Ticket> getBookedTickets(User user);

    static void validateUser(User user) {
        if (Objects.isNull(user)) {
            throw new NullPointerException("User is [null]");
        }
        if (Objects.isNull(user.getEmail())||user.getEmail().isEmpty()) {
            throw new NullPointerException("User's email is empty. User: [" + user + "]");
        }
        if (Objects.isNull(user.getName())||user.getName().isEmpty()) {
            throw new NullPointerException("User's name is empty. User: [" + user + "]");
        }
    }


}
