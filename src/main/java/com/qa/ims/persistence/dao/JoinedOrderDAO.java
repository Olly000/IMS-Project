package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.JoinedOrder;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JoinedOrderDAO {

    public static final Logger LOGGER = LogManager.getLogger();

    public JoinedOrder modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long customerId = resultSet.getLong("customer_id");
        String firstName = resultSet.getString("first_name");
        String surname = resultSet.getString("surname");
        float totalCost = resultSet.getFloat("total_cost");
        String itemName = resultSet.getString("item_name");
        int count = resultSet.getInt("count");
        return new JoinedOrder(orderId, customerId, firstName, surname,
                totalCost, itemName, count);
    }

    /**
     * Creates joined rows containing information from customer, order, order_basket and items
     * for the order it is passed and returns them as a joined order object
     * @param order - the order that the row is based on
     * @return JoinedOrder
     */
    public List<JoinedOrder> constructRow(Order order) {
        List<JoinedOrder> allRows = new ArrayList<>();
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     " WITH co AS (SELECT o.id AS order_id, o.customer_id, c.first_name, c.surname, o.total_cost FROM orders o JOIN customers c ON o.customer_id = c.id), contents AS (SELECT ob.order_id, ob.item_id, i.item_name, COUNT(i.id) AS count FROM order_basket ob JOIN items i ON ob.item_id = i.id WHERE ob.order_id = ? GROUP BY i.id) SELECT co.order_id, co.customer_id, co.first_name, co.surname, co.total_cost, con.item_name, con.count FROM co JOIN contents con ON co.order_id = con.order_id; ")) {
            statement.setLong(1, order.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    allRows.add(modelFromResultSet(resultSet));
            }
                return allRows;
            }
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
