package lk.ijse.restaurantManagement.view.tdm;

import com.jfoenix.controls.JFXButton;

public class ResevationCartTM {
    private String ReservationId;
    private String date;
    private  String time;
    private String tableId;
    private int tablesQty;
    private JFXButton btnRemove;

    public ResevationCartTM() {
    }

    public ResevationCartTM(String reservationId, String date, String time, String tableId, int tablesQty, JFXButton btnRemove) {
        ReservationId = reservationId;
        this.date = date;
        this.time = time;
        this.tableId = tableId;
        this.tablesQty = tablesQty;
        this.btnRemove = btnRemove;
    }

    public String getReservationId() {
        return ReservationId;
    }

    public void setReservationId(String reservationId) {
        ReservationId = reservationId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public int getTablesQty() {
        return tablesQty;
    }

    public void setTablesQty(int tablesQty) {
        this.tablesQty = tablesQty;
    }

    public JFXButton getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(JFXButton btnRemove) {
        this.btnRemove = btnRemove;
    }

    @Override
    public String toString() {
        return "ResevationCartTM{" +
                "ReservationId='" + ReservationId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", tableId='" + tableId + '\'' +
                ", tablesQty=" + tablesQty +
                ", btnRemove=" + btnRemove +
                '}';
    }
}
