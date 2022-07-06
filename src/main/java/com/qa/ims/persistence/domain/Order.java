package com.qa.ims.persistence.domain;
import java.sql.Date;

import java.util.Objects;

public class Order {

    private Long id;

    private Long customerId;

    private float totalCost; // this will be derived from the OrderBasket table

    private Date date;

    // Constructor for retrieving order from the db
    public Order(Long id, Long customerId, float totalCost) {
        this.id = id;
        this.customerId = customerId;
        this.totalCost = totalCost;
    }

    // Constructor for updating an order
    public Order(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    // Constructor for creating an order
    public Order(Long customerId) {
        this.customerId = customerId;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
        return getCustomerId() == order.getCustomerId() && Float.compare(order.getTotalCost(), getTotalCost()) == 0 && Objects.equals(getId(), order.getId()) && Objects.equals(getDate(), order.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getTotalCost(), getDate());
    }
}



