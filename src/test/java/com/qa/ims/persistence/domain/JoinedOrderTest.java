package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

public class JoinedOrderTest {

    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(JoinedOrder.class).verify();
    }
}