package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAOTest {

    private final ItemDAO DAO = new ItemDAO();

    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {
        final Item created = new Item(3L,"cookie", 1f, 25);
        assertEquals(created, DAO.create(created));
    }

    @Test
    public void testReadAll() {
        List<Item> expected = new ArrayList<>();
        expected.add(new Item(1L,"doughnut", 1.5f, 10));
        expected.add(new Item(2L, "muffin", 2f, 20));
        assertEquals(expected, DAO.readAll());
    }

    @Test
    public void testReadLatest() {
        assertEquals(new Item(2L, "muffin",  2f, 20), DAO.readLatest());
    }

    @Test
    public void testRead() {
        final long ID = 1L;
        assertEquals(new Item(ID, "doughnut",  1.5f, 10), DAO.read(ID));
    }

    @Test
    public void testUpdate() {
        final long ID = 1L;
        final Item updated = new Item(ID, "cupcake",  2.5f, 20);
        assertEquals(updated, DAO.update(updated));

    }

    @Test
    public void testDelete() {
        assertEquals(1, DAO.delete(1L));
    }

    @Test
    public void testAmendStockLevel() {
        Item item = new Item(1L, "doughnut", 1.5f, 12);
        Long itemId = 1L;
        int quantity = 2;
        assertEquals(item, DAO.amendStockLevel(itemId, quantity));
    }
}
