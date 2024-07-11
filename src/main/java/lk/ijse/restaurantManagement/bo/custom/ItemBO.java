package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.ItemDTO;
import lk.ijse.restaurantManagement.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO extends SuperBO {
    public List<String> getItemIds() throws SQLException;

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
    public  boolean updateItem(ItemDTO dto) throws SQLException;
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;


    public  boolean deleteItem(String description) throws SQLException;

    public  boolean saveItem(ItemDTO dto) throws SQLException;

    public  int getItemCount() throws SQLException;

    public ItemDTO searchItem(String id) throws SQLException;

    public String autoGenarateItemId() throws SQLException;
}
