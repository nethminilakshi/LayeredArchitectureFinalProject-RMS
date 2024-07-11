package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.ReservationDetailsDAO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.entity.ReservationDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetailsDAOImpl implements ReservationDetailsDAO {

    public  boolean save(List<ReservationDetail> rdList) throws SQLException {
        for (ReservationDetail rd : rdList) {
            if(!save(rd)) {
                return false;
            }
        }
        return true;
    }

    public boolean save(ReservationDetail entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Reservation_Details VALUES(?, ?, ?)",
                entity.getReservationId(),
                entity.getTableId(),
                entity.getReqTablesQty());
    }

    @Override
    public boolean update(ReservationDetail dto) throws SQLException {
        return false;
    }

    @Override
    public ReservationDetail search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String contact) throws SQLException {
        return false;
    }


    public ArrayList<ReservationDetail> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Reservation_details");

        ArrayList<ReservationDetail> reservationDetailsList = new ArrayList<>();
        while (resultSet.next()) {
            String reservationId = resultSet.getString(1);
            String tableId = resultSet.getString(2);
            int reqTablesQty = Integer.parseInt(resultSet.getString(3));


            ReservationDetail reservationDetails = new ReservationDetail(reservationId, tableId, reqTablesQty);
            reservationDetailsList.add(reservationDetails);
        }
        return reservationDetailsList;
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
