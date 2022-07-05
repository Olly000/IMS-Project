package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Item {

    private int id;

    private String name;

    private float cost;

    private int number_in_stock;

    // Constructor for retrieving items from the db
    public Item(int id, String name, float cost, int number_in_stock) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.number_in_stock = number_in_stock;
    }

    //Constructor for adding items to the db
    public Item(String name, float cost, int number_in_stock) {
        this.name = name;
        this.cost = cost;
        this.number_in_stock = number_in_stock;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getNumber_in_stock() {
        return number_in_stock;
    }

    public void setNumber_in_stock(int number_in_stock) {
        this.number_in_stock = number_in_stock;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId() && Float.compare(item.getCost(), getCost()) == 0
                && getNumber_in_stock() == item.getNumber_in_stock() && getName().equals(item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCost(), getNumber_in_stock());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", number_in_stock=" + number_in_stock +
                '}';
    }
}
