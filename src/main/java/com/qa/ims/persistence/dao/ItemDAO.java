package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemDAO implements Dao<Item> {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Item modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String itemName = resultSet.getString("item_name");
        float itemCost = resultSet.getFloat("item_cost");
        int numberInStock = resultSet.getInt("number_in_stock");
        return new Item(id, itemName, itemCost, numberInStock);
    }

    /**
     * Reads all items from the database
     *
     * @return A list of items
     */
    @Override
    public List<Item> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM items;");) {
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(modelFromResultSet(resultSet));
            }
            return items;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Reads the most recent item added to the database
     * @return Item
     */
    public Item readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery("SELECT * FROM items WHERE id = (SELECT MAX(id) FROM items);")) {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Adds an item to the database and returns it when successful
     *
     * @param item
     * @return Item
     */
    @Override
    public Item create(Item item) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO items(item_name, item_cost, number_in_stock) VALUES (?, ?, ?)");) {
            statement.setString(1, item.getItemName());
            statement.setFloat(2, item.getCost());
            statement.setInt(3, item.getNumberInStock());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Returns item with index id
     * @param id
     * @return Item
     */
    @Override
    public Item read(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM items WHERE id = ?");) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                resultSet.next();
                return modelFromResultSet(resultSet);
            }
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Updates an item in the database and returns the result on success
     *
     * @param item
     * @return Item
     */
    @Override
    public Item update(Item item) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(
                             "UPDATE items SET item_name = ?, item_cost = ?, number_in_stock = ? WHERE id = ?");) {
            statement.setString(1, item.getItemName());
            statement.setFloat(2, item.getCost());
            statement.setInt(3, item.getNumberInStock());
            statement.setLong(4, item.getId());
            statement.executeUpdate();
            return read(item.getId());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes item with index id from the database returns 0 on success
     *
     * @param id
     * @return 0
     */
    @Override
    public int delete(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM items WHERE id = ?");) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

}


