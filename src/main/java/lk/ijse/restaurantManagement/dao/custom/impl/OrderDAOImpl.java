package lk.ijse.restaurantManagement.dao.custom.impl;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.OrderDAO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

   /* public String currentId(String orderId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute( "SELECT orderId FROM orders ORDER BY orderId desc LIMIT 1");
        String oId= null;
        if (resultSet.next()) {
         oId = resultSet.getString(1);
        }
        return oId;
    }

    */

    public boolean save(Order entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO orders VALUES(?, ?, ?,?)",
                entity.getOrderId(),
                entity.getOrderType(),
                entity.getCusId(),
                entity.getDate());
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }


    public Order search(String orderId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Orders WHERE orderId = ?", orderId);

        Order order = null;

        if (resultSet.next()) {
            String orderid = resultSet.getString(1);
            String orderType = resultSet.getString(2);
            String cusId = resultSet.getString(3);
            String date = resultSet.getString(4);

            order = new Order(orderid, orderType, cusId, date);
        }
        return order;

    }

    @Override
    public boolean delete(String contact) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT orderId from Orders order by orderId desc limit 1");
        // Salary salary = null;

        if (resultSet.next()) {
            String orderId = resultSet.getString("orderId");
            String numericPart = orderId.replaceAll("\\D+", "");
            int newOrderId = Integer.parseInt(numericPart) + 1;
            return String.format("Od%03d", newOrderId);

        } else {
            return "Od001";
        }
    }

    public  void OrdersCount(BarChart<String,Number> barChartOrders) throws SQLException {

        ResultSet resultSet = SQLUtil.execute( "SELECT date AS order_date, COUNT(*) AS order_count FROM Orders GROUP BY date ORDER BY order_date");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        while (resultSet.next()) {
            String date = resultSet.getString("order_date");
            int ordersCount = resultSet.getInt("order_count");
            series.getData().add(new XYChart.Data<>(date, ordersCount));
        }

        barChartOrders.getData().add(series);


    }
}
