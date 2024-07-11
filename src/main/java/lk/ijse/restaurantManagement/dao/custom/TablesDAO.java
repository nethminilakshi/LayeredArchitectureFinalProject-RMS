package lk.ijse.restaurantManagement.dao.custom;

import lk.ijse.restaurantManagement.dao.CrudDAO;
import lk.ijse.restaurantManagement.entity.ReservationDetail;
import lk.ijse.restaurantManagement.entity.Tables;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TablesDAO extends CrudDAO<Tables> {
   // public boolean save(Tables entity) throws SQLException;

  //  public boolean update(Tables entity) throws SQLException;

  //  public boolean delete(String description) throws SQLException;

   // public ArrayList<Tables> getAll() throws SQLException;

   // public Tables search(String description) throws SQLException;

   // public List<String> getIds() throws SQLException;

    public boolean updateQty(ReservationDetail entity) throws SQLException;

    public boolean updateQty(List<ReservationDetail> entity) throws SQLException;

   // public String autoGenarateId() throws SQLException;
}

