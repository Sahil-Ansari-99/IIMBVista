package com.iimbvista.iimbvista.Model;

public class EventsModelNew {

    String title;
    String img_url;
    String register_url;
    String deadline;
    String description;
    String dates;

    public EventsModelNew(String title, String img_url, String register_url, String deadline, String description, String dates) {
        this.title = title;
        this.img_url = img_url;
        this.register_url = register_url;
        this.deadline = deadline;
        this.description = description;
        this.dates = dates;

    }

    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getRegister_url() {
        return register_url;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getDates() {
        return dates;
    }
}
