package com.epam.beans.daos;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 20/2/16
 * Time: 6:50 PM
 */
public abstract class AbstractDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    protected Session getCurrentSession() {return sessionFactory.getCurrentSession();}

    protected Criteria createBlankCriteria(Class clazz) {return getCurrentSession().createCriteria(clazz);}
}
