package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


import java.util.ArrayList;

import java.util.List;

public class OrderDAO implements Dao<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private final OrderBasketDAO basketDAO = new OrderBasketDAO();

    /**
     * Creates an Order object from a resultSet
     * @param resultSet - the result of querying the orders table
     * @return Order
     * @throws SQLException if order cannot be created
     */
    @Override
    public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long customerId = resultSet.getLong("customer_id");
        float totalCost = resultSet.getFloat("total_cost");
        return new Order(id, customerId, totalCost);
    }

    /**
     * Outputs all the orders in the db to the logger in order table format
     * @return List<Order>
     */
    @Override
    public List<Order> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders;")) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(modelFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Outputs all the orders in the db to the logger in order table format
     * @return List<Order>
     */
    public List<Order> readAllSimple() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders;")) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(modelFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Outputs the most recent order in the database
     * @return Order
     */
    public Order readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery("SELECT * FROM orders WHERE id = (SELECT MAX(id) FROM orders);")) {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new order to the database
     * @param order - the order to be inserted into the db
     * @return Order
     */
    @Override
    public Order create(Order order) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO orders(customer_id, total_cost) VALUES (?, ?)")) {
            statement.setLong(1, order.getCustomerId());
            statement.setFloat(2, order.getTotalCost());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Returns the order with index id
     * @param id - index of order to be read
     * @return Order
     */
    @Override
    public Order read(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return modelFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;    }


    /**
     * Updates the totalCost of the order with index id and returns the result
     * @param order - the order to be updated
     * @return Order
     */
    @Override
    public Order update(Order order) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(
                             "UPDATE orders SET total_cost = ?  WHERE id = ?")) {
            statement.setFloat(1, order.getTotalCost());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
            return read(order.getId());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes order with index id from the database along with all entries relating to the order in the
     * order_basket table, returns 0 on success
     * @param orderId - the id of the order to be deleted.
     * @return 0
     */
    @Override
    public int delete(Long orderId) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?")) {
            statement.setLong(1, orderId);
            basketDAO.deleteAllFromOrder(orderId);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }


}
