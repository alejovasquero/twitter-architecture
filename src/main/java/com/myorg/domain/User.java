package com.myorg.domain;

public class User {
    private String id;
    private String name;
    private String email;
    private String city;

    public User(String id, String name, String email, String city) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.city = city;
    }
}
