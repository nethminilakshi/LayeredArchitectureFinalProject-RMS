package lk.ijse.restaurantManagement.dto;

import java.io.Serializable;

public class OrderDetailDTO implements Serializable {
    private String orderId;
    private String itemId;
    private int qty;
    private double unitPrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String itemId, int qty, double unitPrice) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
