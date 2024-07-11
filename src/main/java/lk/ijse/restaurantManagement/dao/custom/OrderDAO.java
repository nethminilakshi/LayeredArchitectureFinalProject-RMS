package lk.ijse.restaurantManagement.dao.custom;

import javafx.scene.chart.BarChart;
import lk.ijse.restaurantManagement.dao.CrudDAO;
import lk.ijse.restaurantManagement.entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
  //  public String currentId(String orderId) throws SQLException;

    //public boolean save(Order entity) throws SQLException;


    //public Order search(String orderId) throws SQLException;

   // public String autoGenarateId() throws SQLException;

    public  void OrdersCount(BarChart<String,Number> barChartOrders) throws SQLException;
}
