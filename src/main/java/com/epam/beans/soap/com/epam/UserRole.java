//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.30 at 01:22:05 PM ALMT 
//


package com.epam.beans.soap.com.epam;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="userRole"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="REGISTERED_USER"/&gt;
 *     &lt;enumeration value="BOOKING_MANAGER"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "userRole")
@XmlEnum
public enum UserRole {

    REGISTERED_USER,
    BOOKING_MANAGER;

    public String value() {
        return name();
    }

    public static UserRole fromValue(String v) {
        return valueOf(v);
    }

}
