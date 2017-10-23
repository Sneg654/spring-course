package com.epam.beans.models;

public enum UserRole {
    REGISTERED_USER("REGISTERED_USER"),
    BOOKING_MANAGER("BOOKING_MANAGER");

    String roleType;


    UserRole(String roleType) {
        this.roleType = roleType;
    }

}
