package com.iimbvista.iimbvista.Model;

public class Sponsors {
    private String title, url, description, webUrl;

    public Sponsors(String title, String url, String description, String webUrl) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getWebUrl() {
        return webUrl;
    }
}

