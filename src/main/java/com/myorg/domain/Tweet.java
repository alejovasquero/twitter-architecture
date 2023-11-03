package com.myorg.domain;

public class Tweet {
    
    private String text;
    private User user;

    public Tweet(String text, User user) {
        this.text = text;
        this.user = user;
    }
}
