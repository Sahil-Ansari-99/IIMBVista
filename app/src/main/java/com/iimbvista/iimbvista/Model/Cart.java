package com.iimbvista.iimbvista.Model;

public class Cart {
    String title,cost;

    public Cart(String title, String cost) {
        this.title = title;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public String getCost() {
        return cost;
    }
}
