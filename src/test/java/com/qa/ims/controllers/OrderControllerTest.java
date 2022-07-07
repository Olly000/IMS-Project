package com.qa.ims.controllers;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.JoinedOrderDAO;
import com.qa.ims.persistence.dao.OrderBasketDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
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
public class OrderControllerTest {

    @Mock
    private Utils utils;

    @Mock
    private OrderDAO dao;

    @Mock
    private OrderBasketDAO basketDAO;

    @Mock
    private JoinedOrderDAO joinedDAO;

    @InjectMocks
    private OrderController controller;

    @Test
    public void testReadAll() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1L, 1L, 5f));

        Mockito.when(dao.readAll()).thenReturn(orderList);

        assertEquals(orderList, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }

    @Test
    public void testUpdateChoiceValidateTrue() {
        String correct = "ADD";

        assertEquals(correct, controller.updateChoiceValidate(correct));
    }

    @Test
    public void testUpdateChoiceValidateFalse() {
        String wrong = "NOPE";
        String defaultReturn = "ASK";

        assertEquals(defaultReturn, controller.updateChoiceValidate(wrong));
    }

    @Test
    public void testDelete() {

        Long orderId = 1L;

        Mockito.when(utils.getLong()).thenReturn(orderId);

        assertEquals(0, controller.delete());
    }
    }
