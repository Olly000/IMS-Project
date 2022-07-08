package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.OrderBasket;
import com.qa.ims.utils.DBUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderBasketDAOTest {

    private final OrderBasketDAO DAO = new OrderBasketDAO();

    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreateOneEntry() {
        Long order = 1L;
        Long item =  2L;
        float newPrice = 7f;

        assertEquals(newPrice, DAO.createOneEntry(order, item), 1);
    }

    @Test
    public void testCalculateTotal() {
        float total = 5f;
        assertEquals(total, DAO.calculateTotal(1L), 1);
    }

    @Test
    public void testAddItemsToOrder() {
        final Long ID = 1L;
        final Long ITEM = 1L;
        final int QUANTITY = 3;

        assertTrue(DAO.addItemsToOrder(ID, ITEM, QUANTITY));
    }

    @Test
    public void testQuantityOfItemInOrder() {
        Long orderId = 1L;
        Long itemId = 1L;
        int expected = 2;

        assertEquals(expected, DAO.quantityOfItemInOrder(orderId, itemId));
    }

    @Test
    public void testReturnItemsToStock() { // this just tests if method runs ok - not the outcome
                                           // due to DAO interaction issues
        Long orderId = 1L;

        assertNotNull(DAO.returnItemsToStock(orderId));
    }

    @Test
    public void testDelete() {
        assertEquals(1, DAO.delete(1L));
    }

    @Test
    public void testDeleteAllFromOrder() {
        assertEquals(1, DAO.deleteAllFromOrder(1L));
    }

    @Test
    public void testDeleteItemFromOrder() {
        assertEquals(3.5, DAO.deleteItemFromOrder(1L, 1L), 1);
    }

}

