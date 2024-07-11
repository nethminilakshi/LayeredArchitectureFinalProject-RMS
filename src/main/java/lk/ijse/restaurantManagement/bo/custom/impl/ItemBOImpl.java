package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.ItemBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.ItemDAO;
import lk.ijse.restaurantManagement.dto.ItemDTO;
import lk.ijse.restaurantManagement.dto.OrderDetailDTO;
import lk.ijse.restaurantManagement.entity.Item;
import lk.ijse.restaurantManagement.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Item);
    public List<String> getItemIds() throws SQLException {

           return itemDAO.getIds();
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
    public  boolean updateItem(ItemDTO dto) throws SQLException {

        return itemDAO.update(new Item(
                dto.getId(),
                dto.getDescription(),
                dto.getQtyOnHand(),
                dto.getUnitPrice(),
                dto.getStatus()));
        }
        public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {

            ArrayList<Item> items = itemDAO.getAll();
            ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
            for (Item i: items ){
                ItemDTO itemDTO = new ItemDTO(
                i.getId(),
                i.getDescription(),
                i.getQtyOnHand(),
                i.getUnitPrice(),
                i.getStatus());

                itemDTOS.add(itemDTO);
            }
           return itemDTOS;
        }


        public  boolean deleteItem(String description) throws SQLException {

            return itemDAO.delete(description);
        }

        public  boolean saveItem(ItemDTO dto) throws SQLException {

            return itemDAO.save(new Item(
                    dto.getId(),
                    dto.getDescription(),
                    dto.getQtyOnHand(),
                    dto.getUnitPrice(),
                    dto.getStatus()
            ));
        }

        public  int getItemCount() throws SQLException {

            return itemDAO.getCount();
        }

        public ItemDTO searchItem(String id) throws SQLException {

          Item item = itemDAO.search(id);
          ItemDTO itemDTO = new ItemDTO(
                  item.getId(),
                  item.getDescription(),
                  item.getQtyOnHand(),
                  item.getUnitPrice(),
                  item.getStatus());
          return itemDTO;

        }

        public String autoGenarateItemId() throws SQLException {

           return itemDAO.autoGenarateId();
        }

    }


