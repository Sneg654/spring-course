package com.epam.beans.daos.inmemory;

import com.epam.beans.daos.AuditoriumDAO;
import com.epam.beans.models.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository(value = "inMemoryAuditoriumDAO")
public class InMemoryAuditoriumDAO implements AuditoriumDAO {

    private final List<Auditorium> db;

    @Autowired
    public InMemoryAuditoriumDAO(@Value("#{auditoriumList}") List<Auditorium> db) {
        this.db = db;
    }

    @Override
    public List<Auditorium> getAll() {
        return db.stream().collect(Collectors.toList());
    }

    @Override
    public Auditorium getByName(String auditoriumName) {
        return db.stream().filter(auditorium -> Objects.equals(auditorium.getName(), auditoriumName)).findFirst()
                 .orElseThrow(() -> new IllegalArgumentException(
                         "Auditorium with name: [" + auditoriumName + "] does not exist"));
    }

    @Override
    public void delete(Auditorium auditorium) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Auditorium add(Auditorium auditorium) {
        throw new UnsupportedOperationException("not implemented");
    }
}
