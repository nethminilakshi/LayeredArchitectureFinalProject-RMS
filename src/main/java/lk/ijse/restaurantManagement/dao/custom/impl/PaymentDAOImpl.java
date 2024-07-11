package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.PaymentDAO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    public  boolean save(Payment entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Payment VALUES(?, ?, ?, ?, ?)",
                entity.getPaymentId(),
                entity.getCusId(),
                entity.getOrderId(),
                entity.getPayMethod(),
                entity.getAmount());
    }

    @Override
    public boolean update(Payment dto) throws SQLException {
        return false;
    }

    @Override
    public Payment search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String contact) throws SQLException {
        return false;
    }

    public ArrayList<Payment> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM Payment");

        ArrayList<Payment> payments = new ArrayList<>();
        while (resultSet.next()) {
            String paymentId = resultSet.getString(1);
            String cusId = resultSet.getString(2);
            String orderId = resultSet.getString(3);
            String payMethod = resultSet.getString(4);
            double amount = Double.parseDouble(resultSet.getString(5));
            // String payButton =resultSet.getString(5);


            Payment payment = new Payment(paymentId,cusId, orderId, payMethod, amount);
            payments.add(payment);
        }
        return payments;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT paymentId from Payment order by paymentId desc limit 1");

        if (resultSet.next()) {
            String paymentId = resultSet.getString("paymentId");
            String numericPart = paymentId.replaceAll("\\D+","");
            int newPaymentId = Integer.parseInt(numericPart) + 1;
            return String.format("Pay%03d",newPaymentId);

        }else {
            return "pay001";

        }
    }
}
