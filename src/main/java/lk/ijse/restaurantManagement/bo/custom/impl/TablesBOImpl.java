package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.TablesBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.TablesDAO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.dto.TablesDTO;
import lk.ijse.restaurantManagement.entity.ReservationDetail;
import lk.ijse.restaurantManagement.entity.Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesBOImpl implements TablesBO {

    TablesDAO tablesDAO = (TablesDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Tables);

    public boolean saveTables(TablesDTO dto) throws SQLException {

      return tablesDAO.save(new Tables(
              dto.getTableId(),
              dto.getDescription(),
              dto.getNoOfTables(),
              dto.getNoOfSeats()
       ));
    }

    public boolean updateTables(TablesDTO dto) throws SQLException {

            return tablesDAO.update(new Tables(
                    dto.getTableId(),
                dto.getDescription(),
                dto.getNoOfTables(),
                dto.getNoOfSeats()));
    }

    public boolean deleteTables(String description) throws SQLException {

        return tablesDAO.delete(description);
    }
    public ArrayList<TablesDTO> getAllTables() throws SQLException, ClassNotFoundException {

        ArrayList<Tables> tables = tablesDAO.getAll();
        ArrayList<TablesDTO> tablesDTOS = new ArrayList<>();
        for (Tables t: tables) {
            TablesDTO tablesDTO= new TablesDTO(
                    t.getTableId(),
                    t.getDescription(),
                    t.getNoOfTables(),
                    t.getNoOfSeats()
            );
            tablesDTOS.add(tablesDTO);
        }
        return tablesDTOS;
    }

    public TablesDTO searchTables(String description) throws SQLException {

       Tables tables = tablesDAO.search(description);
       TablesDTO tablesDTO = new TablesDTO(
               tables.getTableId(),
               tables.getDescription(),
               tables.getNoOfTables(),
               tables.getNoOfSeats()
       );
       return tablesDTO;
    }
    public List<String> getTablesIds() throws SQLException {

       return tablesDAO.getIds();

    }

    public String autoGenarateTablesId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT tableId from Tables order by tableId desc limit 1");

        if (resultSet.next()) {
            String tableId = resultSet.getString("tableId");
            String numericPart = tableId.replaceAll("\\D+", "");
            int newOrderId = Integer.parseInt(numericPart) + 1;
            return String.format("Od%03d", newOrderId);

        } else {
            return "Od001";
        }
    }
}
