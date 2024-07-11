package lk.ijse.restaurantManagement.dto;

import java.io.Serializable;

public class TablesDTO implements Serializable {
    private String tableId;
    private String description;
    private int noOfTables;
    private int noOfSeats;

    public TablesDTO() {
    }
    public TablesDTO(String tableId, String description, int noOfTables, int noOfSeats) {
        this.tableId = tableId;
        this.description = description;
        this.noOfTables = noOfTables;
        this.noOfSeats = noOfSeats;
    }
    public String getTableId() {
        return tableId;
    }
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getNoOfTables() {
        return noOfTables;
    }
    public void setNoOfTables(int noOfTables) {
        this.noOfTables = noOfTables;
    }
    public int getNoOfSeats() {
        return noOfSeats;
    }
    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    @Override
    public String toString() {
        return "TablesDTO{" +
                "tableId='" + tableId + '\'' +
                ", description='" + description + '\'' +
                ", noOfTables=" + noOfTables +
                ", noOfSeats=" + noOfSeats +
                '}';
    }
}
