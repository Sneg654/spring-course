package com.epam.beans.utils.xml.impl;

import com.epam.beans.models.Auditorium;
import com.epam.beans.models.Event;
import com.epam.beans.models.Rate;
import com.epam.beans.utils.DateConverter;
import com.epam.beans.utils.xml.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("theEventsDomParser")
public class EventsDOMParser implements DOMParser<List<Event>> {

    @Autowired
    @Qualifier("dateConverter")
    private DateConverter dateConverter;


    @Override
    public List<Event> apply(InputStream in) {
        List<Event> result = new ArrayList<>();
        try {
            Document document = DOMParser.buildDocument(in);
            result.add(parseDOM(document));
        } catch (Exception e) {
            throw new RuntimeException("Can't parse input stream from event document by DOM Parser", e);
        }
        return result;
    }

    private Event parseDOM(Document document) {
        Event event = new Event();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("event");
        event = parseEvent(event, nList);
        NodeList nList2 = document.getElementsByTagName("auditorium");
        Auditorium auditorium = new Auditorium();
        auditorium = parseAuditorium(auditorium, nList2);
        event.setAuditorium(auditorium);
        return event;
    }


    private Auditorium parseAuditorium(Auditorium auditorium, NodeList nList2) {
        for (int temp = 0; temp < nList2.getLength(); temp++) {
            Node nNode = nList2.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                auditorium.setId(Long.parseLong(eElement.getElementsByTagName("id").item(0).getTextContent()));
                auditorium.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                auditorium.setSeatsNumber(Integer.parseInt(eElement.getElementsByTagName("seats_number").item(0).getTextContent()));
                auditorium.setVipSeats((eElement.getElementsByTagName("vip_seats").item(0).getTextContent()));
            }
        }
        return auditorium;
    }


    private Event parseEvent(Event event, NodeList nList) {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                event.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                String date = eElement.getElementsByTagName("event_date").item(0).getTextContent();
                event.setDateTime(dateConverter.convert(date));
                event.setBasePrice(Double.parseDouble(eElement.getElementsByTagName("base_price").item(0).getTextContent()));
                event.setRate(Rate.valueOf(eElement.getElementsByTagName("rate").item(0).getTextContent()));
            }
        }
        return event;
    }
}
