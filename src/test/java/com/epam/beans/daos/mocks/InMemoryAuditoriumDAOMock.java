package com.epam.beans.daos.mocks;

import com.epam.beans.daos.inmemory.InMemoryAuditoriumDAO;
import com.epam.beans.models.Auditorium;

import java.util.List;

public class InMemoryAuditoriumDAOMock extends InMemoryAuditoriumDAO {

    public InMemoryAuditoriumDAOMock(List<Auditorium> auditoriums) {
        super(auditoriums);
    }
}
