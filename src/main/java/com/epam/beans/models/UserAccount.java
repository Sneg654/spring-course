package com.epam.beans.models;

public class UserAccount {

    private long id;

    private User user;

    private Double prepaidUserMoney;

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

    public Double getPrepaidUserMoney() {
        return prepaidUserMoney;
    }

    public void setPrepaidUserMoney(Double prepaidUserMoney) {
        this.prepaidUserMoney = prepaidUserMoney;
    }
}
