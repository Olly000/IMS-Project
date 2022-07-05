package com.qa.ims.persistence.domain;

import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private int id;

    private int customerId;

    private float totalCost;

    private LocalDateTime date;

    // Constructor for retrieving order from the db
    public Order(int id, int customerId, float totalCost, LocalDateTime date) {
        this.id = id;
        this.customerId = customerId;
        this.totalCost = totalCost;
        this.date = date;
    }

    // Constructor for creating or updating an order
    public Order(int customerId, float totalCost, LocalDateTime date) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", totalCost=" + totalCost +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() && getCustomerId() == order.getCustomerId() && Float.compare(order.getTotalCost(), getTotalCost()) == 0 && getDate().equals(order.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getTotalCost(), getDate());
    }
}

