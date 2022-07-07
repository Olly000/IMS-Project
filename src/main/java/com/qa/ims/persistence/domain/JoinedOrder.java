package com.qa.ims.persistence.domain;

import java.util.Objects;

/**
 * Class to hold the resultSet format returned by the prepareOrderOutput method.
 * This joins customer, order, order_basket and item tables to display full
 * information for an order - as it is meant for output only, and is wholly
 * dynamic based on the contents of other tables it does not have a
 * corresponding table in the db schema.  The methods that utilise this class are
 * in the JoinedOrderDAO, and are accessed from the OrderController
 */
public class JoinedOrder {

    public JoinedOrder(Long orderId, Long customerId, String firstName, String surname,
                       float totalCost, String itemName, int count) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.firstName = firstName;
        this.surname = surname;
        this.totalCost = totalCost;
        this.itemName = itemName;
        this.count = count;


    }
    private Long orderId;

    private Long customerId;

    private String firstName;

    private String surname;

    private float totalCost;

    private String itemName;

    private int count;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoinedOrder)) return false;
        JoinedOrder that = (JoinedOrder) o;
        return Float.compare(that.totalCost, totalCost) == 0 && count == that.count && Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(customerId, that.customerId) && Objects.equals(firstName, that.firstName) && Objects.equals(surname, that.surname) && Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), customerId, firstName, surname, totalCost, itemName, count);
    }

    @Override
    public String toString() {
        return "JoinedOrder{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", totalCost=" + totalCost +
                ", itemName='" + itemName + '\'' +
                ", count=" + count +
                '}';
    }
}
