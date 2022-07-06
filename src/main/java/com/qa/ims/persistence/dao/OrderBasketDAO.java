package com.qa.ims.persistence.dao;

import com.qa.ims.controller.ItemController;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderBasket;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderBasketDAO {

    public static final Logger LOGGER = LogManager.getLogger();

    private ItemDAO itemDAO = new ItemDAO();

    /**
     *
     * @return
     */


    public List<OrderBasket> readAll() {
        return null;
    }


    public OrderBasket read(Long id) {
        return null;
    }


    public int create(Long orderID, Long itemID) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO order_basket(order_id, item_id) VALUES (?, ?)");) {
            statement.setLong(1, orderID);
            statement.setLong(2, itemID);
            statement.executeUpdate();
            return 1;
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;    }

    /*public float calculateItemTotal(Long itemID, int quantity) {
        float price = itemDAO.read(itemID).getCost();
        return price * quantity;
    }*/

    public float calculateTotal(Long orderID) {
        float subTotal = 0;
        try (Connection connection = DBUtils.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(
        "WITH basket AS (SELECT o.item_id, i.id, i.item_cost FROM order_basket o JOIN items i ON " +
                "o.item_id = i.id WHERE o.id = ?)" +
                "SELECT SUM(item_cost) FROM basket;");) {
            statement.setLong(1, orderID);
            ResultSet result = statement.executeQuery();
            return result.getFloat("SUM(item_cost)");

        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }


    public boolean addItemsToOrder(Long orderID, Long itemID, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.create(orderID, itemID);
        }
        return true;
    }

    public OrderBasket update(OrderBasket orderBasket) {
        return null;
    }


    /**
     * Deletes the entry with primary key id from the db and returns 0 if successful
     * NB - this method is for an individual row of the order_basket table, use
     * this.deleteAllFromOrder to delete all of the rows associated with a particular
     * order id.
     * @param id - the index of the order/item relationship
     * @return
     */
    public int delete(Long id) {

        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM order_basket WHERE id = ?");) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * Deletes all entries in the order_basket table related to an order with id
     * of orderID
     * @param orderID
     * @return
     */
    public int deleteAllFromOrder(Long orderID) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "DELETE FROM order_basket WHERE order_id = ?");) {
            statement.setLong(1, orderID);
            return statement.executeUpdate();
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



    public OrderBasket modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long orderID = resultSet.getLong("order_id");
        Long itemID = resultSet.getLong("item_id");
        return new OrderBasket(id, orderID, itemID);
    }
}
