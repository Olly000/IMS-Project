package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class ItemTest {


    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(Item.class).verify();
    }

    @Test
    public void testSetters() {
        Item item = new Item("cupcake", 2.5f, 10);
        item.setItemName("bun");
        item.setCost(1.5f);
        item.setNumberInStock(5);

        assertEquals("bun", item.getItemName());
        assertEquals(1.5f, item.getCost(), 1);
        assertEquals(5, item.getNumberInStock());
    }


}