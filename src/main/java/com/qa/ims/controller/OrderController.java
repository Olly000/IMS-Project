package com.qa.ims.controller;

import com.qa.ims.persistence.dao.OrderBasketDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


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
        String answer;
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
    public Order create() { // not too keen on the way this creates an order then subsequently
                            // uses update to set the cost but the orderId needs to be generated
                            // for the orderBasket entities to be added
        LOGGER.info("Please enter the customer ID: ");
        Long customerId = utils.getLong();
        Order order = orderDAO.create(new Order(customerId));
        itemAdder(order.getId());
        order.setTotalCost(basketDAO.calculateTotal(order.getId()));
        orderDAO.update(order);
        LOGGER.info("Order number " + order.getId() + " created, total cost is: " + order.getTotalCost());
        return order;
    }

    private float addAnItem(Long orderID) {
        LOGGER.info("Enter ID of item to add to order number " + orderID);
        Long itemID = utils.getLong();
        return basketDAO.createOneEntry(orderID, itemID);
    }

    private float removeAnItem(Long orderID) {
        LOGGER.info("Enter ID of item to remove from order number " + orderID);
        Long itemID = utils.getLong();
        return basketDAO.deleteItemFromOrder(orderID, itemID);

    }

    private String choiceValidate(String userInput) {
        switch (userInput) {
            case "ADD":
                return userInput;
            case "REMOVE":
                return userInput;
            default:
                return "ASK";
        }
    }

    private float updateChoice(Long orderId) { // this could do with some validation
        LOGGER.info("Would you like to add an item to, or remove an item from, order? (ADD/REMOVE)");
        String choice = "ASK";
        while (choice.equals("ASK")) {
            choice = choiceValidate(utils.getString().toUpperCase());
            if (choice.equals("ADD")) {
                return addAnItem(orderId);
            } else if (choice.equals("REMOVE")) {
                return removeAnItem(orderId);
            } else {
                LOGGER.info("Invalid choice, please enter ADD or REMOVE");
            }
        }

        return 0;
        }

    @Override
    public Order update() {
        LOGGER.info("Please enter the id of the order you want to update");
        Long orderId = utils.getLong();
        Order originalOrder = orderDAO.read(orderId);
        originalOrder.setTotalCost(updateChoice(orderId)); // this just adds to the entity
        if (Math.round(originalOrder.getTotalCost()) == 0) {
            orderDAO.delete(orderId);
            return null;
        } else {
            orderDAO.update(originalOrder);
            LOGGER.info("Order Updated");
        }
        return orderDAO.read(originalOrder.getId());
    }

    @Override
    public int delete() {
        LOGGER.info("Please enter the id of the order you would like to delete");
        Long orderId = utils.getLong();
        orderDAO.delete(orderId);
        LOGGER.info("Order number " + orderId + " has been deleted");
        return 0;


    }
}
