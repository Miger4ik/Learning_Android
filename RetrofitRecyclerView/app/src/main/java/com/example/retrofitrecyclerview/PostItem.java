package com.example.retrofitrecyclerview;

public class PostItem {

    private int id;
    private int userId;
    private String title;
    private String body;

    public PostItem(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.body = post.getBody();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
