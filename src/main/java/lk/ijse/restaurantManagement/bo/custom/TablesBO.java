package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dto.TablesDTO;
import lk.ijse.restaurantManagement.entity.Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TablesBO extends SuperBO {
    public boolean saveTables(TablesDTO dto) throws SQLException;

    public boolean updateTables(TablesDTO dto) throws SQLException;

    public boolean deleteTables(String description) throws SQLException;
    public ArrayList<TablesDTO> getAllTables() throws SQLException, ClassNotFoundException;

    public TablesDTO searchTables(String description) throws SQLException;
    public List<String> getTablesIds() throws SQLException;

    public String autoGenarateTablesId() throws SQLException;
}
