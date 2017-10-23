package com.epam.beans.soap;

import com.epam.beans.models.User;
import com.epam.beans.services.UserService;
import com.epam.beans.soap.com.epam.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class SoapUserEndpoint {

    private static final String NAMESPACE = "http://epam.com";

    private UserService userService;

    @Autowired
    public SoapUserEndpoint(@Qualifier("userServiceImpl")UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        User user = userService.getById(request.getId());
        response.setUser(user);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "registerUserRequest")
    @ResponsePayload
    public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) {
        RegisterUserResponse response = new RegisterUserResponse();
        com.epam.beans.models.User user = new com.epam.beans.models.User(request.getUser());
        User newUser = userService.register(user);
        response.setUser(newUser);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "removeUserRequest")
    @ResponsePayload
    public RemoveUserResponse removeUser(@RequestPayload RemoveUserRequest request) {
        RemoveUserResponse response = new RemoveUserResponse();
        long id = request.getId();
        GetUserByIdRequest getByIdRequest = new GetUserByIdRequest();
        getByIdRequest.setId(id);
        User userToDelete = getUserById(getByIdRequest).getUser();
        userService.remove(userToDelete);
        return response;
    }
}
