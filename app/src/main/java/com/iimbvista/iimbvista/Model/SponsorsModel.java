package com.iimbvista.iimbvista.Model;

public class SponsorsModel {
    String name, title, url;

    public SponsorsModel(String name, String title, String url) {
        this.name = name;
        this.title = title;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}

