package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.ReservationBO;
import lk.ijse.restaurantManagement.bo.custom.ReservationDetailsBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.ReservationDetailsDAO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.entity.ReservationDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetailsBOImpl implements ReservationDetailsBO {

    ReservationDetailsDAO reservationDetailsDAO = (ReservationDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ReserveDetails);
    public  boolean saveReservation(List<ReservationDetailDTO> rdList) throws SQLException {
        for (ReservationDetailDTO rd : rdList) {
            if(!saveReservations(rd)) {
                return false;
            }
        }
        return true;
    }

    public boolean saveReservations(ReservationDetailDTO dto) throws SQLException {

        return reservationDetailsDAO.save(new ReservationDetail(
                dto.getReservationId(),
                dto.getTableId(),
                dto.getReqTablesQty()
        ));
    }


    public ArrayList<ReservationDetailDTO> getAllReservations() throws SQLException {

        ArrayList<ReservationDetail> reservationDetails = reservationDetailsDAO.getAll();
        ArrayList<ReservationDetailDTO> reservationDetailDTOS = new ArrayList<>();

        for (ReservationDetail r: reservationDetails){
            ReservationDetailDTO reservationDetailDTO = new ReservationDetailDTO(
                    r.getReservationId(),
                    r.getTableId(),
                    r.getReqTablesQty()
            );
            reservationDetailDTOS.add(reservationDetailDTO);
        }
        return reservationDetailDTOS;
    }


}
