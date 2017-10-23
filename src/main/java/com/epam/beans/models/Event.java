package com.epam.beans.models;

import com.epam.beans.adaptors.LocalDateTimeAdapter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Event", propOrder = {"id", "name", "rate", "basePrice", "dateTime", "auditorium", "auditoriumName"})
public class Event {

    private long id;
    private String name;
    private Rate rate;

    @NumberFormat
    private double basePrice;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateTime;
    private Auditorium auditorium;

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

    private String auditoriumName;

    public Event() {
    }

    public Event(String name, Rate rate, double basePrice, LocalDateTime dateTime, Auditorium auditorium) {
        this(-1, name, rate, basePrice, dateTime, auditorium);
    }

    public Event(long id, String name, Rate rate, double basePrice, LocalDateTime dateTime, Auditorium auditorium) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.basePrice = basePrice;
        this.dateTime = dateTime;
        this.auditorium = auditorium;
        if (auditorium != null) {
            setAuditoriumName(auditorium.getName());
        }
    }

    public Event(com.epam.beans.soap.com.epam.Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.rate = Rate.valueOf(event.getRate().name());
        this.basePrice = event.getBasePrice();
        this.dateTime = LocalDateTime.parse(event.getDateTime());
        this.auditorium = new Auditorium();
        auditorium.setId(event.getAuditorium().getId());
        auditorium.setSeatsNumber(event.getAuditorium().getSeatsNumber());
        auditorium.setVipSeats(event.getAuditorium().getVipSeats());
        if (auditorium != null) {
            setAuditoriumName(auditorium.getName());
        }
    }

    public Event withId(Long eventId) {
        return new Event(eventId, this.name, this.rate, this.basePrice, this.dateTime, this.auditorium);
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Rate getRate() {
        return rate;
    }


    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public double getBasePrice() {
        return basePrice;
    }


    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }


    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        setAuditoriumName(auditorium.getName());
        this.auditorium = auditorium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Event))
            return false;

        Event event = (Event) o;

        if (id != event.id)
            return false;
        if (Double.compare(event.basePrice, basePrice) != 0)
            return false;
        if (name != null ? !name.equals(event.name) : event.name != null)
            return false;
        if (rate != event.rate)
            return false;
        if (dateTime != null ? !dateTime.equals(event.dateTime) : event.dateTime != null)
            return false;
        return auditorium != null ? auditorium.equals(event.auditorium) : event.auditorium == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        temp = Double.doubleToLongBits(basePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (auditorium != null ? auditorium.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", basePrice=" + basePrice +
                ", dateTime=" + dateTime +
                ", auditorium=" + auditorium +
                '}';
    }
}
