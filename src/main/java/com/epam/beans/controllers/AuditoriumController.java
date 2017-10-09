package com.epam.beans.controllers;

import com.epam.beans.models.Auditorium;
import com.epam.beans.services.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuditoriumController {

    @Autowired
    @Qualifier("auditoriumServiceImpl")
    private AuditoriumService auditoriumServiceImpl;

    /**
     * List of auditoriums to get data from Database
     */
    private List<Auditorium> auditoriumList = new ArrayList<>();

    /**
     * Saves the static list of events in model and renders it
     * via freemarker template.
     *
     * @param model
     * @return The index view (FTL)
     */
    @RequestMapping(value = "/auditoriums")
    public String getAllAuditoriums(ModelMap model){
        auditoriumList = auditoriumServiceImpl.getAuditoriums();
        model.addAttribute("auditoriumList", auditoriumList);
        return "auditoriums";
    }

    /**
     * Create new auditorium
     *
     * @param auditorium
     * @return Redirect to /auditoriums page to display auditorium list
     */
    @RequestMapping(value = "/createAuditorium", method = RequestMethod.POST)
    public String create(@ModelAttribute("event") Auditorium auditorium,
                         ModelMap model) {
        Auditorium createdAuditorium = auditoriumServiceImpl.add(auditorium);

        model.addAttribute("auditoriumList", auditoriumList.add(createdAuditorium));
        model.addAttribute("message",
                "Auditorium " + auditorium.getName() + " was successfully registered");
        return "redirect:auditoriums.ftl";
    }


    /**
     * Remove auditorium
     *
     * @param auditorium
     * @return Redirect to /events page to display events list
     */
    @RequestMapping(value = "/removeAuditorium", method = RequestMethod.POST)
    public String remove(@ModelAttribute("model") Auditorium auditorium) {
        auditoriumServiceImpl.delete(auditorium);
        return "redirect:auditoriums.ftl";
    }


    /**
     * Find auditoriums by name
     *
     * @param name
     * @return Redirect to /events page to display events list
     */
    @RequestMapping(value = "/auditoriumsByName", method = RequestMethod.GET)
    public String getByName(@ModelAttribute("name") String name, ModelMap model) throws Exception {
        if(!auditoriumList.isEmpty()) auditoriumList.clear();
        Auditorium auditorium = auditoriumServiceImpl.getByName(name);
        auditoriumList.add(auditorium);
        model.addAttribute("auditoriumList", auditoriumList);
        return "found-auditoriums";
    }
}
