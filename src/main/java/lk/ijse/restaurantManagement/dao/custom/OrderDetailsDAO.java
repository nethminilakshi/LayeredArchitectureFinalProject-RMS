package lk.ijse.restaurantManagement.dao.custom;

import lk.ijse.restaurantManagement.dao.CrudDAO;
import lk.ijse.restaurantManagement.db.DBConnection;
import lk.ijse.restaurantManagement.dto.OrderDetailDTO;
import lk.ijse.restaurantManagement.entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail> {
    public boolean save(List<OrderDetail> odList) throws SQLException;

   public   boolean save(OrderDetail entity) throws SQLException;

}
