package com.epam.beans.daos.mocks;

import com.epam.beans.daos.db.EventDAOImpl;
import com.epam.beans.models.Event;

import java.util.List;

public class EventDAOMock extends EventDAOImpl {

    private final List<Event> events;

    public EventDAOMock(List<Event> events) {
        this.events = events;
    }

    public void init() {
        cleanup();
        events.forEach(this :: create);
    }

    public void cleanup() {
        System.out.println("deleting ");
        System.out.println(getAll());
        getAll().forEach(this :: delete);
    }
}
