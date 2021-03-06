package com.epam.beans.services;

import com.epam.beans.daos.AuditoriumDAO;
import com.epam.beans.models.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("auditoriumServiceImpl")
@Transactional
public class AuditoriumServiceImpl implements AuditoriumService {

    private final AuditoriumDAO auditoriumDAO;

    @Autowired
    public AuditoriumServiceImpl(@Qualifier("auditoriumDAO") AuditoriumDAO auditoriumDAO) {
        this.auditoriumDAO = auditoriumDAO;
    }

    @Override
    public List<Auditorium> getAuditoriums() {
        return auditoriumDAO.getAll();
    }

    @Override
    public Auditorium getByName(String name) {
        return auditoriumDAO.getByName(name);
    }

    @Override
    public int getSeatsNumber(String auditoriumName) {
        return auditoriumDAO.getByName(auditoriumName).getSeatsNumber();
    }

    @Override
    public List<Integer> getVipSeats(String auditoriumName) {
        return auditoriumDAO.getByName(auditoriumName).getVipSeatsList();
    }
}
