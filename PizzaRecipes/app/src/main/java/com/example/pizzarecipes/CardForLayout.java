package com.example.pizzarecipes;

public class CardForLayout {
    private int imageResource;
    private String headerText;
    private String contentText;

    public CardForLayout () {}

    public CardForLayout(int imageResource, String headerText, String contentText) {
        this.imageResource = imageResource;
        this.headerText = headerText;
        this.contentText = contentText;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
