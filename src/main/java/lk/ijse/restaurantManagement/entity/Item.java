package lk.ijse.restaurantManagement.entity;

public class Item {
    private String Id;
    private String description;
    private String qtyOnHand;
    private  String unitPrice;
    private String status;

    public Item(String id, String description, String qtyOnHand, String unitPrice) {
    }

    public Item(String id, String description, String qtyOnHand, String unitPrice, String status) {
        Id = id;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "Id='" + Id + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand='" + qtyOnHand + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}


