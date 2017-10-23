package com.epam.beans.daos.mocks;

import com.epam.beans.daos.db.UserAccountDAOImpl;
import com.epam.beans.models.UserAccount;

import java.util.List;

public class UserAccountDAOMock extends UserAccountDAOImpl {

    private final List<UserAccount> userAccounts;

    public UserAccountDAOMock(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public void init() {
        cleanup();
        userAccounts.forEach(this::refillAccount);
    }

    public void cleanup() {
        removeAll();
    }
}
