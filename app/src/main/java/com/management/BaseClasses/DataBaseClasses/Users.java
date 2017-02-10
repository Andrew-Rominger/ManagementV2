package com.management.BaseClasses.DataBaseClasses;

/**
 * Created by wesleybanghart on 2/10/17.
 */

public class Users {
    int userId;
    String name;
    String email;

    public Users(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
