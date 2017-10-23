package com.epam.beans.configuration;

import com.epam.beans.soap.SoapEvent;
import com.epam.beans.soap.SoapUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfiguration {

    private static final String DEFAULT_URI  = "http://localhost:8080/ws/usersAndEvents";

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.epam.beans.soap.com.epam");
        return marshaller;
    }

    @Bean
    public SoapUser getSoapUser(Jaxb2Marshaller marshaller) {
        SoapUser soapUser = new SoapUser();
        soapUser.setDefaultUri(DEFAULT_URI);
        soapUser.setMarshaller(marshaller);
        soapUser.setUnmarshaller(marshaller);
        return soapUser;
    }

    @Bean
    public SoapEvent getSoapEvent(Jaxb2Marshaller marshaller) {
        SoapEvent soapUser = new SoapEvent();
        soapUser.setDefaultUri(DEFAULT_URI);
        soapUser.setMarshaller(marshaller);
        soapUser.setUnmarshaller(marshaller);
        return soapUser;
    }
}
