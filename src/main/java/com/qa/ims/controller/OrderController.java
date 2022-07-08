package com.qa.ims.controller;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.JoinedOrderDAO;
import com.qa.ims.persistence.dao.OrderBasketDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.JoinedOrder;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class OrderController implements CrudController<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderDAO orderDAO;

    private ItemDAO itemDAO;
    private OrderBasketDAO basketDAO;

    private JoinedOrderDAO joinedDAO;

    private Utils utils;


    public OrderController(OrderDAO orderDAO, OrderBasketDAO basketDAO, JoinedOrderDAO joinedDAO, ItemDAO itemDAO, Utils utils) {
        super();
        this.orderDAO = orderDAO;
        this.basketDAO = basketDAO;
        this.joinedDAO = joinedDAO;
        this.itemDAO = itemDAO;
        this.utils = utils;
    }

    /**
     * Outputs all current orders to the logger
     * @return List<Order>
     */
    @Override
    public List<Order> readAll() {
        List<Order> orders = orderDAO.readAll();
        for (Order order : orders) {
            List<JoinedOrder> joined = joinedDAO.constructRow(order);
            for (JoinedOrder joinedOrder : joined) {
                LOGGER.info(joinedOrder);
            }
        }
        return orders;
    }

    /**
     * Adds entries to the OrderBasket table from user input, returns true if successful
     * @param orderId - id of the order to be added to
     * @return - boolean
     */
    public boolean itemAdder(Long orderId) {
        String answer;
        do {
            LOGGER.info("Please enter the id of the item: ");
            Long itemId = utils.getLong();
            LOGGER.info("Please enter the quantity of item required");
            int quantity = utils.getInt();
            basketDAO.addItemsToOrder(orderId, itemId, quantity); // adds rows to the OrderBasket table
            itemDAO.amendStockLevel(itemId, -quantity); // amends the stock level in the items table
            LOGGER.info("Input END to finish adding items, ITEM to add more");
            answer = utils.getString().toUpperCase();

        } while (!answer.equals("END"));

        return true;
    }

    /**
     * Creates a new order from user input
     * @return - the order created
     */
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

    /**
     *
     * @param orderId - adds an item to the orderBasket from user input
     * @return - float - the new cost of the order
     */
    private float addAnItem(Long orderId) {
        LOGGER.info("Enter ID of item to add to order number " + orderId);
        Long itemId = utils.getLong();
        itemDAO.amendStockLevel(itemId, -1); // quantity is negative as adding an item to the order removes it from stock
        return basketDAO.createOneEntry(orderId, itemId);
    }

    /**
     *
     * @param orderId - removes an item from the orderBasket from user input
     * @return - float - the new cost of the order
     */
    private float removeAnItem(Long orderId) {
        LOGGER.info("Enter ID of item to remove from order number " + orderId);
        Long itemId = utils.getLong();
        itemDAO.amendStockLevel(itemId, 1);
        return basketDAO.deleteItemFromOrder(orderId, itemId);

    }

    /**
     * validates the user input when user asked to choose to add or remove an item
     * @param userInput - the input from the scanner
     * @return - user input if valid, 'ASK' if invalid
     */
    public String updateChoiceValidate(String userInput) {
        switch (userInput) {
            case "ADD":
                return userInput;
            case "REMOVE":
                return userInput;
            default:
                return "ASK";
        }
    }

    /**
     * Provides control to allow the user to choose to add or remove an item when they
     * select the update option from the order menu
     * @param orderId - the id of the order to be updated
     * @return - the new price of the order, or 0 if unsuccessful
     */
    private float updateChoice(Long orderId) { // this could do with some validation
        LOGGER.info("Would you like to add an item to, or remove an item from, order? (ADD/REMOVE)");
        String choice = "ASK";
        while (choice.equals("ASK")) {
            choice = updateChoiceValidate(utils.getString().toUpperCase());
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

    /**
     * Allows user to update an order based on their input
     * @return - returns the updated order
     */
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

    /**
     * Allows the user to delete an order based upon their input
     * @return int 0 on success;
     */
    @Override
    public int delete() {
        LOGGER.info("Please enter the id of the order you would like to delete");
        Long orderId = utils.getLong();
        basketDAO.returnItemsToStock(orderId);
        orderDAO.delete(orderId);
        LOGGER.info("Order number " + orderId + " has been deleted");
        return 0;
    }
}
