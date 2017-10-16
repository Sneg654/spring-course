package com.epam.beans.daos.db;

import com.epam.beans.daos.AbstractDAO;
import com.epam.beans.daos.UserDAO;
import com.epam.beans.models.Booking;
import com.epam.beans.models.Ticket;
import com.epam.beans.models.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Repository(value = "userDAO")
@Transactional
public class UserDAOImpl extends AbstractDAO implements UserDAO {

    @Override
    public User create(User user) {
        UserDAO.validateUser(user);
        User byEmail = getByEmail(user.getEmail());
        User byLogin = getByLogin(user.getLogin());
        if (Objects.nonNull(byEmail)||Objects.nonNull(byLogin)) {
            throw new IllegalStateException(
                    String.format("Unable to store user: [%s]. User with such login or email: [%s] {%s} is already created.", user,
                                  user.getEmail(), user.getLogin()));
        } else {
            TransactionDefinition txDef = new DefaultTransactionDefinition();
            TransactionStatus txStatus = transactionManager.getTransaction(txDef);
            Long userId;
            try {
                userId  = (Long) getCurrentSession().save(user);
                transactionManager.commit(txStatus);
           } catch(Exception e){
                transactionManager.rollback(txStatus);
                throw e;
            }
            return user.withId(userId);
        }
    }

    @Override
    public void delete(User user) {
        UserDAO.validateUser(user);
        getCurrentSession().delete(user);
    }

    @Override
    public User get(long id) {
        return getCurrentSession().get(User.class, id);
    }


    @Override
    public User getByEmail(String email) {
        return ((User) createBlankCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult());
    }

    @Override
    public User getByLogin(String login) {
        return ((User) createBlankCriteria(User.class).add(Restrictions.eq("login", login)).uniqueResult());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllByName(String name) {
        return createBlankCriteria(User.class).add(Restrictions.eq("name", name)).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return ((List<User>) createBlankCriteria(User.class).list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> getBookedTickets(User user) {
        Criteria criteria = getCurrentSession().createCriteria(Booking.class, "Booking")
                .createAlias("Booking.ticket", "ticket")
                .add(Restrictions.eq("Booking.id", user.getId()))
                .setProjection(Projections.property("ticket"));
           return criteria.list();
    }

}
