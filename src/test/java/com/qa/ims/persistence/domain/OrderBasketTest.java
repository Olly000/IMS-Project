package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderBasketTest {

    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(OrderBasket.class).verify();
    }

    @Test
    public void testSetters() {
        OrderBasket basket = new OrderBasket(2L,3L);
        basket.setOrderId(1L);
        basket.setItemId(1L);

        assertEquals(Long.valueOf(1L), basket.getOrderId());
        assertEquals(Long.valueOf(1L), basket.getItemId());
    }
}