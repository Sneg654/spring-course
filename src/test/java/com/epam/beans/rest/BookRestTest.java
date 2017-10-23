package com.epam.beans.rest;

import com.epam.beans.configuration.AppConfiguration;
import com.epam.beans.configuration.HttpMessagesConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, HttpMessagesConfiguration.class})
@Transactional
@EnableAspectJAutoProxy(proxyTargetClass = true)
@WebAppConfiguration
public class BookRestTest {


    @Autowired
    private RestTemplate restTemplate;

    private static final String URL = "http://localhost:8080";
    private static final String JSON = "{ \"eventId\":\"90\", \"userMail\":\"batman@mail.ru\", \"airDate\":\"2017-07-20\", \"airTime\":\"00:00:00.000000000\", \"seats\":\"3,4\"}";

    @Test
    public void testGetTicketPrice() {
        HttpHeaders headers = new HttpHeaders();
        final String FULL_PATH = "http://localhost:8080/rest/tickets/price?eventId=90&userMail=batman@mail.ru&airDate=2017-07-20&airTime=00:00:00.000000000&seats=3,4";
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(FULL_PATH,HttpMethod.GET, entity, String.class);
        System.out.println("Price for ticket " + response.getBody());
        assertTrue("Price should be > 0 ", Double.valueOf(response.getBody())>0);
    }

    @Test
    public void testBookTicket() {
        HttpHeaders headers = new HttpHeaders();
        final String CONTENT = "/rest/tickets/buy";
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(JSON, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL + CONTENT, HttpMethod.POST, entity, String.class);
        System.out.println("Booked ticket is " + response.getBody());
        assertNotNull(response.getBody());
    }


    @Test
    public void testGetTicketsPDF(){
        final String CONTENT = "/rest/tickets/ticketsPDF";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType("application", "pdf")));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Object response = restTemplate.exchange(URL + CONTENT, HttpMethod.GET, entity, String.class);
        String body = (String)((ResponseEntity)response).getBody();
        byte[] resultByteArr = body.getBytes();
        assertNotNull(resultByteArr);
    }
}
