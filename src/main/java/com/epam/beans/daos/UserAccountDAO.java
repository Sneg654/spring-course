package com.epam.beans.daos;

import com.epam.beans.models.User;
import com.epam.beans.models.UserAccount;

import java.util.List;
import java.util.Objects;

public interface UserAccountDAO {

    UserAccount getAccountByUser(User user);

    UserAccount refillAccount(UserAccount userAccount);

    List<UserAccount> getAll();

    void withdraw(User user, double amount);

    static void validateUserAccount(UserAccount userAccount) {
        if (Objects.isNull(userAccount)) {
            throw new IllegalArgumentException("UserAccount cannot be null");
        }
        UserDAO.validateUser(userAccount.getUser());
        if (userAccount.getPrepaidUserMoney() < 0) {
            throw new IllegalArgumentException("Amount cannot be < 0");
        }
    }
}
