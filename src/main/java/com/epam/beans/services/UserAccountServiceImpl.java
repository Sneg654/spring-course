package com.epam.beans.services;

import com.epam.beans.daos.UserAccountDAO;
import com.epam.beans.daos.UserDAO;
import com.epam.beans.models.User;
import com.epam.beans.models.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userAccountServiceImpl")
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountDAO userAccountDAO;

    @Autowired
    public UserAccountServiceImpl(@Qualifier("userAccountDAOImpl") UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public UserAccount getAccountByUser(User user) {
        return userAccountDAO.getAccountByUser(user);
    }

    @Override
    public UserAccount refillAccount(UserAccount userAccount) {
        UserAccountDAO.validateUserAccount(userAccount);
        return userAccountDAO.refillAccount(userAccount);
    }

    @Override
    public List<UserAccount> getAll() {
        return userAccountDAO.getAll();
    }

    @Override
    public void withdraw(User user, double amount) {
        UserDAO.validateUser(user);
        userAccountDAO.withdraw(user, amount);
    }
}
