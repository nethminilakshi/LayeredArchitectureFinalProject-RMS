package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.PaymentDTO;
import lk.ijse.restaurantManagement.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    public  boolean savePayment(PaymentDTO dto) throws SQLException;

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;

    public String autoGenaratePaymentId() throws SQLException;
}
