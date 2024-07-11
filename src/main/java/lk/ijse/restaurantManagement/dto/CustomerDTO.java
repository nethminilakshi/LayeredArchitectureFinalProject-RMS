package lk.ijse.restaurantManagement.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private String cusId;
    private String name;
    private String address;
    private String contact;
    private String email;

    public CustomerDTO() {
    }

    public CustomerDTO(String cusId, String name, String address, String contact, String email) {
        this.cusId = cusId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "cusId='" + cusId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
