package com.epam.beans.daos.mocks;

import com.epam.beans.daos.db.UserDAOImpl;
import com.epam.beans.models.User;

import java.util.List;

public class UserDAOMock extends UserDAOImpl {

    private final List<User> users;

    public UserDAOMock(List<User> users) {
        this.users = users;
    }

    public void init() {
        cleanup();
        users.forEach(this :: create);
    }

    public void cleanup() {
        getAll().forEach(this :: delete);
    }
}
