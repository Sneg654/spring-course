package com.epam.beans.services;

import com.epam.beans.models.User;
import com.epam.beans.models.UserAccount;

import java.util.List;

public interface UserAccountService {

    UserAccount getAccountByUser(User user);

    UserAccount refillAccount(UserAccount userAccount);

    List<UserAccount> getAll();

    void withdraw(User user, double amount);

}
