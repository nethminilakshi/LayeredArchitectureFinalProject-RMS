package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.OrderDetailDTO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseOrderBO extends SuperBO {
    public  boolean placeOrder(String orderId, String orderType, String cusId, String date, List<OrderDetailDTO> details) throws SQLException;
    public  boolean updateItemQty(OrderDetailDTO dto) throws SQLException;


    public boolean updateQty(List<OrderDetailDTO> odList) throws SQLException;

    public boolean updateTablesQty(ReservationDetailDTO dto) throws SQLException;
    public boolean updateQtyTables(List<ReservationDetailDTO> rdList) throws SQLException;

}
