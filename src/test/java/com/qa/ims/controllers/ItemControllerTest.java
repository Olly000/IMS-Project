package com.qa.ims.controllers;

import com.qa.ims.controller.ItemController;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @Mock
    private Utils utils;

    @Mock
    private ItemDAO dao;

    @InjectMocks
    private ItemController controller;

    @Test
    public void testCreate() {
        final String ITEMNAME = "Doughnut";
        final float COST = 1.5f;
        final int STOCK = 10;
        final Item created = new Item(ITEMNAME, COST, STOCK);

        Mockito.when(utils.getString()).thenReturn(ITEMNAME);
        Mockito.when(utils.getFloat()).thenReturn(COST);
        Mockito.when(utils.getInt()).thenReturn(STOCK);
        Mockito.when(dao.create(created)).thenReturn(created);

        assertEquals(created, controller.create());

        Mockito.verify(utils, Mockito.times(1)).getString();
        Mockito.verify(utils, Mockito.times(1)).getFloat();
        Mockito.verify(utils, Mockito.times(1)).getInt();
    }

    @Test
    public void testReadAll() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1L, "Doughnut", 1.5f, 10));

        Mockito.when(dao.readAll()).thenReturn(itemList);

        assertEquals(itemList, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }

    @Test
    public void testUpdate() {
        Item updated = new Item(1L, "Muffin", 2f, 20);

        Mockito.when(utils.getLong()).thenReturn(1L);
        Mockito.when(utils.getString()).thenReturn("Muffin");
        Mockito.when(utils.getFloat()).thenReturn(2f);
        Mockito.when(utils.getInt()).thenReturn(20);
        Mockito.when(dao.update(updated)).thenReturn(updated);

        assertEquals(updated, this.controller.update());

        Mockito.verify(this.utils, Mockito.times(1)).getLong();
        Mockito.verify(this.utils, Mockito.times(1)).getString();
        Mockito.verify(this.utils, Mockito.times(1)).getFloat();
        Mockito.verify(this.utils, Mockito.times(1)).getInt();
        Mockito.verify(this.dao, Mockito.times(1)).update(updated);
    }

    @Test
    public void testDelete() {
        final long ID = 1L;

        Mockito.when(utils.getLong()).thenReturn(ID);
        Mockito.when(dao.delete(ID)).thenReturn(1);

        assertEquals(1L, this.controller.delete());

        Mockito.verify(utils, Mockito.times(1)).getLong();
        Mockito.verify(dao, Mockito.times(1)).delete(ID);
    }








}
