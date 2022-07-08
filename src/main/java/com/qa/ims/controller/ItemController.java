package com.qa.ims.controller;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Takes in item details to enable CRUD functionality
 */

public class ItemController implements CrudController<Item> {

    public static final Logger LOGGER = LogManager.getLogger();

    private ItemDAO itemDAO;

    private Utils utils;

    public ItemController(ItemDAO itemDAO, Utils utils) {
        super();
        this.itemDAO = itemDAO;
        this.utils = utils;
    }

    /**
     * Output all items to the logger
     * @return List<Item>
      */
    @Override
    public List<Item> readAll() {
        List<Item> items = itemDAO.readAll();
        for (Item item : items) {
            LOGGER.info(item);
        }
        return items;
    }

    /**
     * Creates a new item from user input
     * @return Item item
     */
    @Override
    public Item create() {
        LOGGER.info("Please enter the name of the item: ");
        String name = utils.getString();
        LOGGER.info("Please enter the cost of the item: ");
        float cost = utils.getFloat();
        LOGGER.info("Please enter the number of items in stock");
        int numberInStock = utils.getInt();
        Item item = itemDAO.create(new Item(name, cost, numberInStock));
        LOGGER.info("Item created");
        return item;
    }

    /**
     * Updates an existing item from data in user input
     * @return Item item
     */
    @Override
    public Item update() {
        LOGGER.info("Please enter the id of the item you would like to update: ");
        Long id = utils.getLong();
        LOGGER.info("Please enter an item name: ");
        String name = utils.getString();
        LOGGER.info("Please enter the cost of the item: ");
        float cost = utils.getFloat();
        LOGGER.info("Please enter the number in stock:");
        int numberInStock = utils.getInt();
        Item item = itemDAO.update(new Item(id, name, cost, numberInStock));
        LOGGER.info("Item Updated");
        return item;
    }

    /**
     * Deletes an item from the database based on id from user input
     * @return int id
     */
    @Override
    public int delete() {
        LOGGER.info("Please enter the id of the item you would like to delete");
        Long id = utils.getLong();
        return itemDAO.delete(id);
    }
}
