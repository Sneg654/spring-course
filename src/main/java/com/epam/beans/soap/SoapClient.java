package com.epam.beans.soap;

import com.epam.beans.configuration.SoapConfiguration;

import com.epam.beans.models.Event;

import com.epam.beans.models.User;
import com.epam.beans.soap.com.epam.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class SoapClient {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapConfiguration.class);
        SoapUser soapUser = context.getBean(SoapUser.class);
        SoapEvent soapEvent = context.getBean(SoapEvent.class);
        checkUserSoapService(soapUser);
        checkEventSoapService(soapEvent);
    }

    private static void checkEventSoapService(SoapEvent soapEvent) {
        long eventId= 90;
        GetEventByIdResponse response = soapEvent.getEventById(eventId);

        Event event = response.getEvent();
        System.out.println("Found event " + event);

        com.epam.beans.soap.com.epam.Event newEvent = createFakeEvent();
        CreateEventResponse createdEventResponse = soapEvent.createEvent(newEvent);
        Event createdEvent = createdEventResponse.getEvent();
        System.out.println("Created event is " + createdEvent);

        long deleteId = createdEvent.getId();
        soapEvent.removeEvent(deleteId);
        System.out.println("Event with id " + deleteId + " was deleted");

    }

    private static void checkUserSoapService(SoapUser soapUser) {
        long userId = 11;
        GetUserByIdResponse response = soapUser.getUserById(userId);
        User user = response.getUser();
        System.out.println("Found user " + user);

        com.epam.beans.soap.com.epam.User newUser = createFakeUser();

        RegisterUserResponse registerUserResponse = soapUser.registerUser(newUser);
        User registeredUser = registerUserResponse.getUser();
        System.out.println("New user " + registeredUser + " was registered");

        long deleteId = registeredUser.getId();
        soapUser.removeUser(deleteId);
        System.out.println("User with id " + deleteId + " was deleted");
    }

    private static com.epam.beans.soap.com.epam.User createFakeUser() {
        com.epam.beans.soap.com.epam.User newUser =  new com.epam.beans.soap.com.epam.User();
        newUser.setId(999L);
        newUser.setName(UUID.randomUUID().toString());
        newUser.setEmail(UUID.randomUUID().toString());
        newUser.setLogin(UUID.randomUUID().toString());
        newUser.setPassword(UUID.randomUUID().toString());
        newUser.setBirthday(LocalDate.now().toString());
        newUser.setUserRole(com.epam.beans.soap.com.epam.UserRole.REGISTERED_USER);
        return newUser;
    }

    private static com.epam.beans.soap.com.epam.Event createFakeEvent(){
        com.epam.beans.soap.com.epam.Event newEvent = new com.epam.beans.soap.com.epam.Event();
        newEvent.setId(44L);
        newEvent.setBasePrice(1.1);
        newEvent.setName("aaa");
        newEvent.setRate(Rate.HIGH);
        LocalDateTime now = LocalDateTime.now();
        newEvent.setDateTime(now.toString());
        Auditorium auditorium = new Auditorium();
        auditorium.setId(1);
        auditorium.setName("Blue Hall");
        auditorium.setSeatsNumber(500);
        Long[] vipSeats = new Long[]{25L,26L,27L,28L,29L,30L,31L,32L,33L,34L,35L};
        auditorium.setVipSeats(Arrays.asList(vipSeats).toString());
        newEvent.setAuditorium(auditorium);
        newEvent.setAuditoriumName(auditorium.getName());
        return newEvent;
    }
}
