package com.epam.beans.soap;



import com.epam.beans.soap.com.epam.User;
import com.epam.beans.soap.com.epam.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class SoapUser extends WebServiceGatewaySupport {

    GetUserByIdResponse getUserById(long id) {
        GetUserByIdRequest request = new GetUserByIdRequest();
        request.setId(id);
        return (GetUserByIdResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    RegisterUserResponse registerUser(User user) {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUser(user);
        return (RegisterUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    RemoveUserResponse removeUser(long id) {
        RemoveUserRequest request = new RemoveUserRequest();
        request.setId(id);
        return (RemoveUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
