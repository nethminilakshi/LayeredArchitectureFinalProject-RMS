package lk.ijse.restaurantManagement.view.tdm;

import com.jfoenix.controls.JFXButton;

public class OrderDetailTM {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private String date;
    private JFXButton btnRemove;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String code, String description, int qty, double unitPrice, double total, String date, JFXButton btnRemove) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
        this.date = date;
        this.btnRemove = btnRemove;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public JFXButton getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(JFXButton btnRemove) {
        this.btnRemove = btnRemove;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "id='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", date='" + date + '\'' +
                ", btnRemove=" + btnRemove +
                '}';
    }
    }

