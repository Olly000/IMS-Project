package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

public class OrderBasketTest {

    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(OrderBasket.class).verify();
    }
}