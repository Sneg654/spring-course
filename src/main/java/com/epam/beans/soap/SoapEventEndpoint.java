package com.epam.beans.soap;

import com.epam.beans.models.Event;
import com.epam.beans.services.EventService;
import com.epam.beans.soap.com.epam.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapEventEndpoint {

    private static final String NAMESPACE = "http://epam.com";

    private EventService eventService;

    @Autowired
    public SoapEventEndpoint(@Qualifier("eventServiceImpl")EventService eventService) {
        this.eventService = eventService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getEventByIdRequest")
    @ResponsePayload
    public GetEventByIdResponse getEventById(@RequestPayload GetEventByIdRequest request) {
        GetEventByIdResponse response = new GetEventByIdResponse();
        Event event = eventService.getById(request.getId());
        response.setEvent(event);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createEventRequest")
    @ResponsePayload
    public CreateEventResponse createEvent(@RequestPayload CreateEventRequest request) {
        CreateEventResponse response = new CreateEventResponse();
        com.epam.beans.models.Event event = new com.epam.beans.models.Event(request.getEvent());
        Event newEvent = eventService.create(event);
        response.setEvent(newEvent);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "removeEventRequest")
    @ResponsePayload
    public RemoveEventResponse removeEvent(@RequestPayload RemoveEventRequest request) {
        RemoveEventResponse response = new RemoveEventResponse();
        long id = request.getId();
        GetEventByIdRequest getByIdRequest = new GetEventByIdRequest();
        getByIdRequest.setId(id);
        Event eventToDelete = getEventById(getByIdRequest).getEvent();
        eventService.remove(eventToDelete);
        return response;
    }
}
