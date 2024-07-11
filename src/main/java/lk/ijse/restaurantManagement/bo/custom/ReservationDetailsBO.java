package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.entity.ReservationDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ReservationDetailsBO extends SuperBO {
    public  boolean saveReservation(List<ReservationDetailDTO> rdList) throws SQLException;

    public boolean saveReservations(ReservationDetailDTO dto) throws SQLException;


    public ArrayList<ReservationDetailDTO> getAllReservations() throws SQLException;
}
