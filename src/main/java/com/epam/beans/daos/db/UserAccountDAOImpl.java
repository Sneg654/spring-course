package com.epam.beans.daos.db;

import com.epam.beans.daos.AbstractDAO;
import com.epam.beans.daos.UserAccountDAO;
import com.epam.beans.models.User;
import com.epam.beans.models.UserAccount;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userAccountDAOImpl")
@Transactional
public class UserAccountDAOImpl extends AbstractDAO implements UserAccountDAO {

    @Override
    public UserAccount getAccountByUser(User user) {
        List<UserAccount> userAccounts = (List<UserAccount>) createBlankCriteria(UserAccount.class)
                .add(Restrictions.eq("user", user)).list();
        if(userAccounts.isEmpty()){
            return null;
        }
        return userAccounts.get(0);
    }

    @Override
    public UserAccount refillAccount(UserAccount userAccount) {
        UserAccount foundedAccount = getAccountByUser(userAccount.getUser());
        if (foundedAccount != null) {
            userAccount.setPrepaidUserMoney(foundedAccount.getPrepaidUserMoney() + userAccount.getPrepaidUserMoney());
            userAccount.setId(foundedAccount.getId());
            getCurrentSession().merge(userAccount);
            getCurrentSession().flush();
        } else{
            getCurrentSession().save(userAccount);
            getCurrentSession().flush();
        }
        return userAccount;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getAll() {
        return ((List<UserAccount>) createBlankCriteria(UserAccount.class).list());
    }

    @Override
    public void withdraw(User user, double amount)  {
        UserAccount foundedAccount = getAccountByUser(user);
        if (foundedAccount != null) {
            if(!isEnoughMoney(foundedAccount, amount)){
                throw new IllegalArgumentException("The user: " + user.getName() + " has not enough money to book ticket");
            }
            foundedAccount.setPrepaidUserMoney(foundedAccount.getPrepaidUserMoney() - amount);
            getCurrentSession().merge(foundedAccount);
        } else{
            throw new IllegalArgumentException("There is no account of user: " + user.getName());
        }
    }

    private boolean isEnoughMoney(UserAccount foundedAccount, double amount){
        return !(foundedAccount.getPrepaidUserMoney() < amount);
    }

   protected void removeAll(){
       List<UserAccount> userAccountList = getAll();
       if(!userAccountList.isEmpty()){
           userAccountList.forEach(userAccount -> getCurrentSession().delete(userAccount));
       }
   }

}
