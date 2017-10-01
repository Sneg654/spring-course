package com.epam.beans.daos;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDAO {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {return sessionFactory.getCurrentSession();}

    protected Criteria createBlankCriteria(Class clazz) {return getCurrentSession().createCriteria(clazz);}
}
