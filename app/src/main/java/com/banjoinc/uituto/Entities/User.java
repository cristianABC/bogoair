package com.banjoinc.uituto.Entities;

/**
 * Created by TOSHIBA 2 on 16/09/2017.
 */

public class User {
    private String name;
    private String email;
    private String password;
    private int points;
    private int complaints;

    public User() {
        ;
    }
    public User(String name,String password){
        this.name = name;
        this.password = password;
    }
    public User(String name, String email, String password, int points, int complaints) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.points = points;
        this.complaints = complaints;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public int getComplaints() {
        return complaints;
    }
}
