package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.PaymentBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.PaymentDAO;
import lk.ijse.restaurantManagement.dto.ItemDTO;
import lk.ijse.restaurantManagement.dto.PaymentDTO;
import lk.ijse.restaurantManagement.entity.Item;
import lk.ijse.restaurantManagement.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Pay);

    public  boolean savePayment(PaymentDTO dto) throws SQLException {

        return paymentDAO.save(new Payment(
                dto.getPaymentId(),
                dto.getCusId(),
                dto.getOrderId(),
                dto.getPayMethod(),
                dto.getAmount()));

    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {

        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment p : payments) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    p.getPaymentId(),
                    p.getCusId(),
                    p.getOrderId(),
                    p.getPayMethod(),
                    p.getAmount()

            );
            paymentDTOS.add(paymentDTO);
        }
        return  paymentDTOS;
    }

    public String autoGenaratePaymentId() throws SQLException {

       return paymentDAO.autoGenarateId();
    }
    // if can make search pyment method too...............
}
