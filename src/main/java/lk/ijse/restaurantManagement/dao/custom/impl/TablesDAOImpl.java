package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.TablesDAO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.ReservationDetail;
import lk.ijse.restaurantManagement.entity.Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesDAOImpl implements TablesDAO {

    public boolean save(Tables entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Tables VALUES(?, ?, ?, ?)",
                entity.getTableId(),
                entity.getDescription(),
                entity.getNoOfTables(),
                entity.getNoOfSeats());
    }

    public boolean update(Tables entity) throws SQLException {

        return SQLUtil.execute( "UPDATE Tables SET description = ?, noOfTables = ?, noOfSeats = ? WHERE tableId = ?",
                entity.getDescription(),
                entity.getNoOfTables(),
                entity.getNoOfSeats(),
                entity.getTableId());
    }

    public boolean delete(String description) throws SQLException {

        return SQLUtil.execute("DELETE FROM Tables WHERE tableId = ?", description);
    }

    public ArrayList<Tables> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Tables");
        ArrayList<Tables> tablesList = new ArrayList<>();

        while (resultSet.next()) {
            String tableId = resultSet.getString(1);
            String description = resultSet.getString(2);
            int noOfTables = resultSet.getInt(3);
            int noOfSeats = resultSet.getInt(4);

            Tables tables = new Tables(tableId, description,noOfTables, noOfSeats);
            tablesList.add(tables);
        }
        return tablesList;
    }

    public Tables search(String description) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Tables WHERE tableId = ?", description);

        Tables tables = null;

        if (resultSet.next()) {
            String tableId = resultSet.getString(1);
            String Description = resultSet.getString(2);
            int noOfTables = resultSet.getInt(3);
            int noOfseats = resultSet.getInt(4);


            tables = new Tables(tableId, Description, noOfTables, noOfseats);
        }
        return tables;
    }

    public List<String> getIds() throws SQLException {

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT tableId FROM Tables");

        while(resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;

    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    public boolean updateQty(ReservationDetail entity) throws SQLException {

        return SQLUtil.execute("UPDATE Tables SET noOfTables = noOfTables - ? WHERE tableId = ?",
                entity.getReqTablesQty(),
                entity.getTableId());
    }

    public boolean updateQty(List<ReservationDetail> entity) throws SQLException {
        for (ReservationDetail rd : entity) {
            if (!updateQty(rd)) {
                return false;
            }
        }
        return true;
    }

    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT tableId from Tables order by tableId desc limit 1");

        if (resultSet.next()) {
            String tableId = resultSet.getString("tableId");
            String numericPart = tableId.replaceAll("\\D+", "");
            int newOrderId = Integer.parseInt(numericPart) + 1;
            return String.format("Od%03d", newOrderId);

        } else {
            return "T001";
        }
    }
}
