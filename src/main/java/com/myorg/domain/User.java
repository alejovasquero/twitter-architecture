package com.myorg.domain;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String city;
    private List<User> followers;

    public User(String id, String name, String email, String city) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.city = city;
        this.followers = new ArrayList<>();
    }

    public void addFollower(User user) {
        this.followers.add(user);
    }
}
