package com.qa.ims.persistence.domain;

import java.util.Objects;

public class OrderBasket {

    private int id;

    private int orderId;

    private int itemId;

    // Constructor for retrieving data from the db
    public OrderBasket(int id, int orderId, int itemId) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
    }

    // Constructor for creating/updating data - NB this will not be directly user facing
    public OrderBasket(int orderId, int itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }


    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "OrderBasket{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderBasket)) return false;
        OrderBasket that = (OrderBasket) o;
        return getId() == that.getId() && getOrderId() == that.getOrderId() && getItemId() == that.getItemId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderId(), getItemId());
    }
}
