package lk.ijse.restaurantManagement.dao.custom;

import lk.ijse.restaurantManagement.dao.CrudDAO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.entity.ReservationDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ReservationDetailsDAO extends CrudDAO<ReservationDetail> {
    public  boolean save(List<ReservationDetail> rdList) throws SQLException;

    public boolean save(ReservationDetail entity) throws SQLException;


    public ArrayList<ReservationDetail> getAll() throws SQLException;

}
