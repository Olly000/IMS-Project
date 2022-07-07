package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(Order.class).verify();
    }

    @Test public void testSetters() {
        Order order = new Order(1L, 2L, 3f);
        order.setId(2L);
        order.setCustomerId(3L);
        order.setTotalCost(4f);

        assertEquals(Long.valueOf(2L), order.getId());
        assertEquals(Long.valueOf(3L), order.getCustomerId());
        assertEquals(4f, order.getTotalCost(), 1);
    }
}