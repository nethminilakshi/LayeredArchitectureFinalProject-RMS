package lk.ijse.restaurantManagement.dao.custom;

import lk.ijse.restaurantManagement.dao.CrudDAO;
import lk.ijse.restaurantManagement.entity.Item;
import lk.ijse.restaurantManagement.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> {
   // public  List<String> getIds() throws SQLException;



    public  boolean updateQty(OrderDetail entity) throws SQLException;

    //public  boolean update(ItemDTO itemDTO) throws SQLException;

    public  boolean updateQty(List<OrderDetail> odList) throws SQLException;

   // public ArrayList<Item> getAll() throws SQLException;

  //  public  boolean delete(String description) throws SQLException;

   // public  boolean save(ItemDTO itemDTO) throws SQLException;

   // public  int getCount() throws SQLException;

  //  public Item search(String id) throws SQLException;

   // public String autoGenarateId() throws SQLException;
}
