//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.30 at 01:22:05 PM ALMT 
//


package com.epam.beans.soap.com.epam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Event complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Event"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rate" type="{http://epam.com}rate" minOccurs="0"/&gt;
 *         &lt;element name="basePrice" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="dateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://epam.com}auditorium" minOccurs="0"/&gt;
 *         &lt;element name="auditoriumName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Event", propOrder = {
    "id",
    "name",
    "rate",
    "basePrice",
    "dateTime",
    "auditorium",
    "auditoriumName"
})
public class Event {

    protected long id;
    protected String name;
    @XmlSchemaType(name = "string")
    protected Rate rate;
    protected double basePrice;
    protected String dateTime;
    protected Auditorium auditorium;
    protected String auditoriumName;

    /**
     * Gets the value of the id property.
     *
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the rate property.
     *
     * @return
     *     possible object is
     *     {@link Rate }
     *
     */
    public Rate getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     *
     * @param value
     *     allowed object is
     *     {@link Rate }
     *
     */
    public void setRate(Rate value) {
        this.rate = value;
    }

    /**
     * Gets the value of the basePrice property.
     *
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Sets the value of the basePrice property.
     *
     */
    public void setBasePrice(double value) {
        this.basePrice = value;
    }

    /**
     * Gets the value of the dateTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDateTime(String value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the auditorium property.
     *
     * @return
     *     possible object is
     *     {@link Auditorium }
     *
     */
    public Auditorium getAuditorium() {
        return auditorium;
    }

    /**
     * Sets the value of the auditorium property.
     *
     * @param value
     *     allowed object is
     *     {@link Auditorium }
     *     
     */
    public void setAuditorium(Auditorium value) {
        this.auditorium = value;
    }

    /**
     * Gets the value of the auditoriumName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditoriumName() {
        return auditoriumName;
    }

    /**
     * Sets the value of the auditoriumName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditoriumName(String value) {
        this.auditoriumName = value;
    }

}