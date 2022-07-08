package com.qa.ims.persistence.domain;

import java.util.Objects;

public class OrderBasket {

    private Long id;

    private Long orderId;

    private Long itemId;

    // Constructor for retrieving data from the db
    public OrderBasket(Long id, Long orderId, Long itemId) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
    }

    // Constructor for creating/updating data - NB this will not be directly user facing
    public OrderBasket(Long orderId, Long itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }


    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderBasket)) return false;
        OrderBasket that = (OrderBasket) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(getItemId(), that.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderId(), getItemId());
    }

    @Override
    public String toString() {
        return "OrderBasket{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                '}';
    }
}
