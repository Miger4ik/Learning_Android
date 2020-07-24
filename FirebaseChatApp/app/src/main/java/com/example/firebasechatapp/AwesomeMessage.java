package com.example.firebasechatapp;

public class AwesomeMessage {
    private String text;
    private String name;
    private String sender;
    private String recipient;
    private boolean isMine;
    private String imageUrl;

    public AwesomeMessage() {
    }

    public AwesomeMessage(String text, String name, String sender, String recipient, boolean isMine, String imageUrl) {
        this.text = text;
        this.name = name;
        this.sender = sender;
        this.recipient = recipient;
        this.isMine = isMine;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
