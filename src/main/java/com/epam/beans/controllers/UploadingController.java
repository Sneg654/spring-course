package com.epam.beans.controllers;

import com.epam.beans.models.Event;
import com.epam.beans.models.User;
import com.epam.beans.services.EventService;
import com.epam.beans.services.UserService;
import com.epam.beans.utils.xml.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Controller
public class UploadingController {

    @Autowired
    @Qualifier("theUsersDomParser")
    private DOMParser<List<User>> userDomParser;

    @Autowired
    @Qualifier("theEventsDomParser")
    private DOMParser<List<Event>> eventDomParser;

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventServiceImpl;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @RequestMapping("/uploading-user")
    public String uploadingUser() {
        return "uploading-user";
    }

    @RequestMapping("/uploading-event")
    public String uploadingEvent() {
        return "uploading-event";
    }

    @RequestMapping(value = "/uploading-user", method = RequestMethod.POST)
    public String uploadingUser(@RequestParam("uploadingFiles") MultipartFile multipartFile) throws IOException {
        InputStream xmlFile = multipartFile.getInputStream();
        List<User> userList = userDomParser.apply(xmlFile);
        userList.forEach(user -> userServiceImpl.register(user));
        return "redirect:users.ftl";
    }

    @RequestMapping(value = "/uploading-event", method = RequestMethod.POST)
    public String uploadingEvent(@RequestParam("uploadingFiles") MultipartFile multipartFile) throws IOException {
        InputStream xmlFile = multipartFile.getInputStream();
        List<Event> eventList = eventDomParser.apply(xmlFile);
        eventList.forEach(event -> eventServiceImpl.create(event));
        return "redirect:events.ftl";
    }
}