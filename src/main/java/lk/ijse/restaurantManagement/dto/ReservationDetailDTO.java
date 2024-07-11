package lk.ijse.restaurantManagement.dto;

import java.io.Serializable;

public class ReservationDetailDTO implements Serializable {

    private String reservationId;
    private String tableId;
    private int reqTablesQty;

    public ReservationDetailDTO() {
    }

    public ReservationDetailDTO(String reservationId, String tableId, int reqTablesQty) {
        this.reservationId = reservationId;
        this.tableId = tableId;
        this.reqTablesQty = reqTablesQty;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public int getReqTablesQty() {
        return reqTablesQty;
    }

    public void setReqTablesQty(int reqTablesQty) {
        this.reqTablesQty = reqTablesQty;
    }

    @Override
    public String toString() {
        return "ReservationDetailDTO{" +
                "reservationId='" + reservationId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", reqTablesQty=" + reqTablesQty +
                '}';
    }
}
