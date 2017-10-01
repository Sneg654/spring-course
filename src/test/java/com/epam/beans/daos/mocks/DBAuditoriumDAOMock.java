package com.epam.beans.daos.mocks;

import com.epam.beans.daos.db.AuditoriumDAOImpl;
import com.epam.beans.models.Auditorium;

import java.util.List;


public class DBAuditoriumDAOMock extends AuditoriumDAOImpl {

    private final List<Auditorium> auditoriums;

    public DBAuditoriumDAOMock(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public void init() {
        cleanup();
        auditoriums.forEach(this :: add);
    }

    public void cleanup() {
        getAll().forEach(this :: delete);
    }
}
