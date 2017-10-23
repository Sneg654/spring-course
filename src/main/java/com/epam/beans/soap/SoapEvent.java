package com.epam.beans.soap;

import com.epam.beans.soap.com.epam.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SoapEvent  extends WebServiceGatewaySupport {

    GetEventByIdResponse getEventById(long id) {
        GetEventByIdRequest request = new GetEventByIdRequest();
        request.setId(id);
        return (GetEventByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    CreateEventResponse createEvent(Event event) {
        CreateEventRequest request = new CreateEventRequest();
        request.setEvent(event);
        return (CreateEventResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    RemoveEventResponse removeEvent(long id) {
        RemoveEventRequest request = new RemoveEventRequest();
        request.setId(id);
        return (RemoveEventResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
