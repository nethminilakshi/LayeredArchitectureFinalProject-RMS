package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.PurchaseOrderBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.custom.*;
import lk.ijse.restaurantManagement.db.DBConnection;
import lk.ijse.restaurantManagement.dto.ItemDTO;
import lk.ijse.restaurantManagement.dto.OrderDetailDTO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Item);
    TablesDAO tablesDAO =  (TablesDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Tables);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Order);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.OrderDetails);

    public  boolean placeOrder(String orderId, String orderType, String cusId, String date, List<OrderDetailDTO> details) throws SQLException {
            Connection connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

            try {
                boolean isOrderSaved = orderDAO.save(new Order(orderId,orderType,cusId,date));
                if (isOrderSaved) {
                    for (OrderDetailDTO orderDetails : details) {
                        OrderDetail orderDetail = new OrderDetail(orderDetails.getOrderId(),orderDetails.getItemId(),orderDetails.getQty(),orderDetails.getUnitPrice());
                        boolean isOrderDetailSaved = orderDetailsDAO.save(orderDetail);
                        if (isOrderDetailSaved) {
                            boolean isItemQtyUpdate = itemDAO.updateQty(orderDetail);
                            if (isItemQtyUpdate) {
                                connection.commit();
                                return true;
                            }
                        }
                    }
                }
                connection.rollback();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
                return false;
            } finally {
                connection.setAutoCommit(true);
            }
        }

    public  boolean updateItemQty(OrderDetailDTO dto) throws SQLException {

        return itemDAO.updateQty(new OrderDetail(
                dto.getQty(),
                dto.getItemId()
        ));
    }


    public boolean updateQty(List<OrderDetailDTO> odList) throws SQLException {
        for (OrderDetailDTO od : odList) {
            if (!updateItemQty(od)) {
                return false;
            }
        }
        return true;
    }

    public boolean updateTablesQty(ReservationDetailDTO dto) throws SQLException {

        return tablesDAO.updateQty(new ReservationDetail(
                dto.getReqTablesQty(),
                dto.getTableId()));


    }
    public boolean updateQtyTables(List<ReservationDetailDTO> rdList) throws SQLException {
        for (ReservationDetailDTO rd : rdList) {
            if (!updateTablesQty(rd)) {
                return false;
            }
        }
        return true;
    }

    public ItemDTO findItem(String code) {
        try {
            PurchaseOrderBOImpl purchaseOrderBO = new PurchaseOrderBOImpl();
            return purchaseOrderBO.searchItem(code);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        }
    }
    public ItemDTO searchItem(String id) throws SQLException {

        Item item = itemDAO.search(id);
        ItemDTO itemDTO = new ItemDTO(
                item.getId(),
                item.getDescription(),
                item.getQtyOnHand(),
                item.getUnitPrice(),
                item.getStatus());
        return itemDTO;

    }

    }


