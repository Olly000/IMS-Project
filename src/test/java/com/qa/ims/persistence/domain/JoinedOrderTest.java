package com.qa.ims.persistence.domain;
import com.qa.ims.persistence.domain.JoinedOrder;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JoinedOrderTest {

    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(JoinedOrder.class).verify();
    }

    @Test
    public void testSetters() {
        JoinedOrder joined = new JoinedOrder(1L, 1L,
                "adam", "tan", 5f, "cookie", 4 );
        joined.setOrderId(2L);
        joined.setCustomerId(2L);
        joined.setFirstName("joe");
        joined.setSurname("black");
        joined.setTotalCost(3f);
        joined.setItemName("doughnut");
        joined.setCount(2);

        assertEquals(Long.valueOf(2L), joined.getOrderId());
        assertEquals(Long.valueOf(2L), joined.getCustomerId());
        assertEquals("joe", joined.getFirstName());
        assertEquals("black", joined.getSurname());
        assertEquals(3f, joined.getTotalCost(), 1);
        assertEquals("doughnut", joined.getItemName());
        assertEquals(2, joined.getCount());
    }

}