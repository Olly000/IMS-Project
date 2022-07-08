package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.OrderBasket;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderBasketDAO {

    public static final Logger LOGGER = LogManager.getLogger();

    private ItemDAO itemDAO = new ItemDAO();

    public OrderBasket modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long orderID = resultSet.getLong("order_id");
        Long itemID = resultSet.getLong("item_id");
        return new OrderBasket(id, orderID, itemID);
    }

    /**
     * Adds one orderID, itemID object to the order_basket table then calculates and returns
     * the new total cost of the order
     * @return float
     */
    public float createOneEntry(Long orderID, Long itemID) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO order_basket(order_id, item_id) VALUES (?, ?)")) {
            statement.setLong(1, orderID);
            statement.setLong(2, itemID);
            statement.executeUpdate();
            return calculateTotal(orderID);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;    }

    public float calculateTotal(Long orderID) {
        try (Connection connection = DBUtils.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(
        """
            WITH basket AS (SELECT o.item_id, i.id, i.item_cost AS price 
            FROM order_basket o JOIN items i ON o.item_id = i.id WHERE o.order_id = ?) 
            SELECT SUM(price) FROM basket;""")) {
            statement.setLong(1, orderID);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getFloat("SUM(price)");

        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * Calls the createOneEntry method in a loop to allow user to add multiple items
     * with the same id to the order_basket table.  Returns true on success, false on
     * failure
     * @param orderID - id of the order to have items added
     * @param itemID - id of the item to be added
     * @param quantity - number of items to be added
     * @return Boolean - based on success
     */
    public boolean addItemsToOrder(Long orderID, Long itemID, int quantity) {
        try {
            for (int i = 0; i < quantity; i++) {
                createOneEntry(orderID, itemID);
            }
            return true;
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    /**
     * Deletes the entry with primary key id from the db and returns 0 if successful
     * NB - this method is for an individual row of the order_basket table, use
     * this.deleteAllFromOrder to delete all of the rows associated with a particular
     * order id or deleteItemFromOrder to delete a single entry relating to a specific
     * order and item
     * @param id - the index of the order/item relationship
     * @return
     */
    public int delete(Long id) {

        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM order_basket WHERE id = ?");) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return Math.toIntExact(id);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * Deletes all entries in the order_basket table related to an order with id
     * of orderID.  As I have since set the order_id foreign key of this table to
     * ON DELETE CASCADE this method may have become superfluous
     * @param orderId - id of order whose entries are to be deleted
     * @return
     */
    public int deleteAllFromOrder(Long orderId) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "DELETE FROM order_basket WHERE order_id = ?");) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
            return Math.toIntExact(orderId);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * Deletes a single item from the OB table then calls the recalculateTotal
     * method to
     * @param orderID
     * @param itemID
     * @return
     */
    public float deleteItemFromOrder(Long orderID, Long itemID) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "DELETE FROM order_basket WHERE order_id = ? AND item_id = ? LIMIT 1");) {
            statement.setLong(1, orderID);
            statement.setLong(2, itemID);
            statement.executeUpdate();
            return calculateTotal(orderID);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }
}
