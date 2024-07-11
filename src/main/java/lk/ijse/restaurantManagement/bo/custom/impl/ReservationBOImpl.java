package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.ReservationBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.CustomerDAO;
import lk.ijse.restaurantManagement.dao.custom.ReservationDAO;
import lk.ijse.restaurantManagement.dao.custom.ReservationDetailsDAO;
import lk.ijse.restaurantManagement.dao.custom.TablesDAO;
import lk.ijse.restaurantManagement.db.DBConnection;
import lk.ijse.restaurantManagement.dto.CustomerDTO;
import lk.ijse.restaurantManagement.dto.ReservationDTO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.Reservation;
import lk.ijse.restaurantManagement.entity.ReservationDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationBOImpl implements ReservationBO {

    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Reservation);
    ReservationDetailsDAO dao = (ReservationDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ReserveDetails);
    TablesDAO tablesDAO = (TablesDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Tables);
    public  boolean saveReservation(ReservationDTO dto) throws SQLException {

      return reservationDAO.save(new Reservation(
              dto.getReservationId(),
              dto.getDescription(),
              dto.getCusId(),
              dto.getDate(),
              dto.getTime(),
              dto.getStatus()
      ));

    }

    public ReservationDTO searchReservation(String id) throws SQLException {
        
        Reservation reservation =reservationDAO.search(id);
        ReservationDTO reservationDTO = new ReservationDTO(
                reservation.getReservationId(),
                reservation.getDescription(),
                reservation.getCusId(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getStatus());

        return reservationDTO;
    }

    public List<ReservationDTO> getAllReservations() throws SQLException, ClassNotFoundException {

        ArrayList<Reservation> reservations = reservationDAO.getAll();
        List<ReservationDTO> reservationDTOS = new ArrayList<>();

        for (Reservation r : reservations){
            ReservationDTO reservationDTO = new ReservationDTO(
                    r.getReservationId(),
                    r.getDescription(),
                    r.getCusId(),
                    r.getDate(),
                    r.getTime(),
                    r.getStatus()
            );
            reservationDTOS.add(reservationDTO);
        }
    return reservationDTOS;
    }

public  boolean PlaceReservation(String reservationId,String description,String cusId,String date,String time,String status,List<ReservationDetailDTO> rsDetails) throws SQLException {
    Connection connection = DBConnection.getDbConnection().getConnection();
    connection.setAutoCommit(false);

    try {
        boolean isReservationSaved = reservationDAO.save(new Reservation(reservationId,description,cusId,date,time,status));
        if (isReservationSaved) {
            for (ReservationDetailDTO  dto: rsDetails){
                ReservationDetail reservationDetail = new ReservationDetail(
                        dto.getReservationId(),
                        dto.getTableId(),
                        dto.getReqTablesQty());

            boolean isReservationDetailSaved = dao.save(reservationDetail);
            if (isReservationDetailSaved) {
                boolean isTableQtyUpdate = tablesDAO.updateQty(reservationDetail);
                if (isTableQtyUpdate) {
                    connection.commit();
                    return true;
                }
            }
        }}

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
    public String autoGenarateReservationId() throws SQLException {

       return reservationDAO.autoGenarateId();
    }

}
