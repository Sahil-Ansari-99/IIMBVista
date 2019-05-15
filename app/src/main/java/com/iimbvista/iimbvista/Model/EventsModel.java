package com.iimbvista.iimbvista.Model;

public class EventsModel {
    String title;
    String url;
    String date;
    String time;
    String description;
    String location;
    String cost;

    public EventsModel(String title, String url, String date, String time, String description, String location, String cost) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getCost() {
        return cost;
    }
}
