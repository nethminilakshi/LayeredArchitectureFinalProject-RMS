package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.ReservationDTO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface ReservationBO extends SuperBO {

     boolean PlaceReservation(String reservationId,String description,String cusId,String date,String time,String status,List<ReservationDetailDTO> rsDetails) throws SQLException;

    public  boolean saveReservation(ReservationDTO dto) throws SQLException;

    public ReservationDTO searchReservation(String id) throws SQLException;

    public List<ReservationDTO> getAllReservations() throws SQLException, ClassNotFoundException;


    public String autoGenarateReservationId() throws SQLException;
}
