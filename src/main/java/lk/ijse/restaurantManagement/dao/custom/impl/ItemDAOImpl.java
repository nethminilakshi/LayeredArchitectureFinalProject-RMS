package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.ItemDAO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.Item;
import lk.ijse.restaurantManagement.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    public  List<String> getIds() throws SQLException {

        List<String> idList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT id FROM Item");

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

  /*  public Item search(String description) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Item WHERE description = ?");

        Item item = null;

        if (resultSet.next()) {
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String qtyOnHand = resultSet.getString(3);
            String unitPrice = resultSet.getString(4);
            String status = resultSet.getString(5);

            item = new Item(code, name, qtyOnHand, unitPrice, status);
        }
        return item;
    }

   */


    public  boolean updateQty(OrderDetail entity) throws SQLException {

        return SQLUtil.execute("UPDATE Item SET qtyOnHand = qtyOnHand - ? WHERE id = ?",
                entity.getQty(),entity.getItemId());
    }

    public  boolean update(Item entity) throws SQLException {

        return SQLUtil.execute("UPDATE Item SET description = ?, qtyOnHand = ?, unitPrice = ?,status = ? WHERE id = ?",
               entity.getDescription(),
                entity.getQtyOnHand(),
                entity.getUnitPrice(),
                entity.getStatus(),
                entity.getId());

    }

    public boolean updateQty(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if (!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Item> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Item");

        ArrayList<Item> itemList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            String qtyOnHand = resultSet.getString(3);
            String unitPrice = resultSet.getString(4);
            String status = resultSet.getString(5);

            Item item = new Item(id, description, qtyOnHand, unitPrice, status);
            itemList.add(item);
        }
        return itemList;
    }

    public  boolean delete(String description) throws SQLException {

        return SQLUtil.execute("DELETE FROM Item WHERE description = ?", description);
    }

    public  boolean save(Item entity) throws SQLException {

        return SQLUtil.execute( "INSERT INTO Item VALUES(?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getDescription(),
                entity.getQtyOnHand(),
                entity.getUnitPrice(),
                entity.getStatus());
    }

    public  int getCount() throws SQLException {

        ResultSet resultSet = SQLUtil.execute( "SELECT COUNT(*) AS item_count FROM Item");

        int itemCount = 0;
        if(resultSet.next()) {
            itemCount = resultSet.getInt("item_count");
        }
        return itemCount;
    }

    public Item search(String id) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Item WHERE Id = ?", id);

        Item item = null;

        if (resultSet.next()) {
            String Id = resultSet.getString(1);
            String  description = resultSet.getString(2);
            String qtyOnHand = resultSet.getString(3);
            String unitPrice= resultSet.getString(4);
            String  status= resultSet.getString(4);

            item = new Item(Id, description, qtyOnHand, unitPrice,status);
        }
        return item;
    }

    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT id from Item order by id desc limit 1");

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String numericPart = id.replaceAll("\\D+","");
            int newId = Integer.parseInt(numericPart) + 1;
            return String.format("P%03d",newId);

        }else {
            return "p001";
        }
    }
}
