package com.qa.ims.controller;

import com.qa.ims.persistence.dao.OrderBasketDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderBasket;
import com.qa.ims.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderController implements CrudController<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderDAO orderDAO;
    private OrderBasketDAO basketDAO;

    private Utils utils;

    public OrderController(OrderDAO orderDAO, OrderBasketDAO basketDAO, Utils utils) {
        super();
        this.orderDAO = orderDAO;
        this.basketDAO = basketDAO;
        this.utils = utils;
    }


    @Override
    public List<Order> readAll() {
        return null;
    }

    public boolean itemAdder(Long orderId) {
        String answer = "";
        float subTotal = 0f;
        do {
            LOGGER.info("Please enter the id of the item: ");
            Long itemId = utils.getLong();
            LOGGER.info("Please enter the quantity of item required");
            int quantity = utils.getInt();
            basketDAO.addItemsToOrder(orderId, itemId, quantity); // adds rows to the OrderBasket table
            LOGGER.info("Input END to finish adding items, ITEM to add more");
            answer = utils.getString().toUpperCase();

        } while (!answer.equals("END"));

        return true;
    }

    @Override
    public Order create() {
        LOGGER.info("Please enter the customer ID: ");
        Long customerId = utils.getLong();
        Order order = orderDAO.create(new Order(customerId));
        itemAdder(order.getId());
        order.setTotalCost(basketDAO.calculateTotal(order.getId()));
        LOGGER.info("Order number" + order.getId() + "created, total cost is: " + order.getTotalCost());
        return order;
    }


    private void updateChoice(Long orderId) {
        String choice = "ASK";
        while (!(choice.equals("ADD") || choice.equals("REMOVE"))) {
            LOGGER.info("Would you like to add or remove an item (enter ADD or REMOVE");
            choice = utils.getString().toUpperCase();
        }

    }

    @Override
    public Order update() {
        LOGGER.info("Please enter the id of the order you want to update");
        Long orderId = utils.getLong();
        LOGGER.info("Would you like to add or remove an item (enter ADD or REMOVE");
        String choice = utils.getString().toUpperCase();
        if (choice )


        Order originalOrder = orderDAO.read(id);
        float newCost = basketDAO.();
        Order order = orderDAO.update(new Order(originalOrder.getCustomerId(), newCost));
        LOGGER.info("Order Updated");
        return order;
    }

    @Override
    public int delete() {
        LOGGER.info("Please enter the id of the order you would like to delete");
        Long id = utils.getLong();
        orderDAO.delete(id);
        basketDAO.deleteAllFromOrder(id);
        LOGGER.info("Order number " + id + " has been deleted");
        return 0;


    }
}
