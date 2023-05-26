package com.jobapps.governmentjobnews.Model;

public class NewsModel {

    private final String name;
    private final String image_url;
    private final String news_url;

    public NewsModel(String name, String image_url, String news_url) {
        this.name = name;
        this.image_url = image_url;
        this.news_url = news_url;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getNews_url() {
        return news_url;
    }
}
