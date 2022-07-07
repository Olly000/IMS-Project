package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Item {

    private Long id;

    private String name;

    private float cost;

    private int numberInStock;

    // Constructor for retrieving items from the db
    public Item(Long id, String name, float cost, int numberInStock) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.numberInStock = numberInStock;
    }

    //Constructor for adding items to the db
    public Item(String name, float cost, int numberInStock) {
        this.name = name;
        this.cost = cost;
        this.numberInStock = numberInStock;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
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

    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Float.compare(item.getCost(), getCost()) == 0 && getNumberInStock() == item.getNumberInStock()
                && Objects.equals(getId(), item.getId()) && Objects.equals(getName(), item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCost(), getNumberInStock());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", numberInStock=" + numberInStock +
                '}';
    }
}
