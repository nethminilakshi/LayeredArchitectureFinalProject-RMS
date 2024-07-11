package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.OrderDetailsDAO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    public boolean save(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail orderDetail : odList) {
            if(!save(orderDetail)) {
                return false;
            }
        }
        return true;
    }

    public boolean save(OrderDetail entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Order_details VALUES(?, ?, ?,?)",
        entity.getOrderId(),
        entity.getItemId(),
        entity.getQty(),
        entity.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException {
        return false;
    }

    @Override
    public OrderDetail search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String contact) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
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

    @Override
    public String autoGenarateId() throws SQLException {
        return null;
    }

}
