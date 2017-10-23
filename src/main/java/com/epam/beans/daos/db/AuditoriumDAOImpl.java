package com.epam.beans.daos.db;

import com.epam.beans.daos.AbstractDAO;
import com.epam.beans.daos.AuditoriumDAO;
import com.epam.beans.models.Auditorium;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository(value = "auditoriumDAO")
@Transactional
public class AuditoriumDAOImpl extends AbstractDAO implements AuditoriumDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Auditorium> getAll() {
        return ((List<Auditorium>) createBlankCriteria(Auditorium.class).list());
    }

    @Override
    public Auditorium getByName(String name) {
        return ((Auditorium) createBlankCriteria(Auditorium.class).add(Restrictions.eq("name", name)).uniqueResult());
    }

    @Override
    public Auditorium add(Auditorium auditorium) {
        Long id = (Long) getCurrentSession().save(auditorium);
        return auditorium.withId(id);
    }

    @Override
    public void delete(Auditorium auditorium) {
        getCurrentSession().delete(auditorium);
    }
}
