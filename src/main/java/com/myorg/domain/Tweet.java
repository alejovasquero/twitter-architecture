package com.myorg.domain;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    
    private String text;
    private User user;
    private List<Comment> comments;
    private int likes = 0;

    public Tweet(String text, User user) {
        this.text = text;
        this.user = user;
        this.comments = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addLike() {
        this.likes++;
    }
}
