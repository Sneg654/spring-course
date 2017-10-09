package com.epam.beans.controllers;

import com.epam.beans.models.Event;
import com.epam.beans.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventServiceImpl;

    /**
     * List of events to get data from Database
     */
    private List<Event> eventList = new ArrayList<>();

    /**
     * Saves the static list of events in model and renders it
     * via freemarker template.
     *
     * @param model
     * @return The index view (FTL)
     */
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model) throws Exception {
        eventList = eventServiceImpl.getAll();
        model.addAttribute("eventList", eventList);
        return "events";
    }

    /**
     * Create new event
     *
     * @param event
     * @return Redirect to /events page to display event list
     */
    @RequestMapping(value = "/createEvent", method = RequestMethod.POST)
    public String create(@ModelAttribute("event") Event event,
                           ModelMap model) {
        Event createdEvent = eventServiceImpl.create(event);

        model.addAttribute("eventList", eventList.add(createdEvent));
        model.addAttribute("message",
                "Event " + event.getName() + " was successfully registered");
        return "redirect:events.ftl";
    }


    /**
     * Remove event
     *
     * @param event
     * @return Redirect to /events page to display events list
     */
    @RequestMapping(value = "/removeEvent", method = RequestMethod.POST)
    public String remove(@ModelAttribute("model") Event event) {
        eventServiceImpl.remove(event);
        return "redirect:events.ftl";
    }


     /**
     * Find events by name
     *
     * @param name
     * @return Redirect to /events page to display events list
     */
    @RequestMapping(value = "/eventsByName", method = RequestMethod.GET)
    public String getByName(@ModelAttribute("name") String name, ModelMap model) throws Exception {
        if(!eventList.isEmpty()) eventList.clear();
        eventList = eventServiceImpl.getByName(name);
        model.addAttribute("eventList", eventList);
        return "found-events";
    }


    /**
     * Find events between dates range
     *
     * @param fromDate
     * @param toDate
     * @return Redirect to /events page to display user events
     */
    @RequestMapping(value = "/eventsByDateRange", method = RequestMethod.GET)
    public String getByDateRange(@ModelAttribute("fromDate") LocalDateTime fromDate,
                                 @ModelAttribute("toDate") LocalDateTime toDate,
                                 ModelMap model)
            throws Exception {
        if(!eventList.isEmpty()) eventList.clear();
        eventList = eventServiceImpl.getForDateRange(fromDate, toDate);
        model.addAttribute("eventList", eventList);
        return "found-events";
    }



    /**
     * Find events after date by name
     *
     * @param toDate
     * @return Redirect to /events page to display events list
     */
    @RequestMapping(value = "/eventsAfterDate", method = RequestMethod.GET)
    public String getAfterDate(@ModelAttribute("toDate") LocalDateTime toDate,
                               ModelMap model)  throws Exception {
        if(!eventList.isEmpty()) eventList.clear();
        eventList = eventServiceImpl.getNextEvents(toDate);
        model.addAttribute("eventList", eventList);
        return "found-events";
    }
}
