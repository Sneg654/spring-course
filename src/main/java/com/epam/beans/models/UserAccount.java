package com.epam.beans.models;

import java.io.Serializable;

public class UserAccount implements Serializable{

    private long id;

    private User user;

    private double prepaidUserMoney;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrepaidUserMoney() {
        return prepaidUserMoney;
    }

    public void setPrepaidUserMoney(double prepaidUserMoney) {
        this.prepaidUserMoney = prepaidUserMoney;
    }
}
