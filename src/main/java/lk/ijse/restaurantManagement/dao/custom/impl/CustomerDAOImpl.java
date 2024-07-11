package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.CustomerDAO;
import lk.ijse.restaurantManagement.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Customer entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Customer VALUES(?, ?, ?, ?, ?)",
                entity.getCusId(),
                entity.getName(),
                entity.getAddress(),
                entity.getContact(),
                entity.getEmail());
    }

    @Override
    public boolean update(Customer entity) throws SQLException {

        return SQLUtil.execute("UPDATE Customer SET cusId = ?, name = ?, address = ?, email = ? WHERE contact = ?",
                entity.getCusId(),
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getContact());
    }
    public Customer search(String id) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer WHERE contact = ?", id);

        Customer customer = null;

        if (resultSet.next()) {
            String cusId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);

            customer = new Customer(cusId, name, address,contact,email);
        }
        return customer;
    }
    public boolean delete(String contact) throws SQLException {

        return SQLUtil.execute("DELETE FROM Customer WHERE contact = ?", contact);
    }

   public ArrayList<Customer> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM customer");

        ArrayList<Customer>customers=new ArrayList<>();
        while (resultSet.next()) {
            String cusId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);

            Customer customer = new Customer(cusId, name, address,contact,email);
            customers.add(customer);
        }
        return customers;
    }


  /* public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
       ArrayList<Customer>customers = customerDAO.getAll();
       ArrayList<CustomerDTO>customerDTOS=new ArrayList<>();
       for(Customer c:customers){
           CustomerDTO customerDTO=new CustomerDTO(
                   c.getCusId(),
                   c.getName(),
                   c.getAddress(),
                   c.getContact(),
                   c.getEmail());

           customerDTOS.add(customerDTO);

       }
       return customerDTOS;
   }

   */
    public  List<String> getIds() throws SQLException { //usage for placeOrderForm

        ResultSet resultSet = SQLUtil.execute("SELECT contact FROM Customer");

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
    public  int getCount() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS customer_count FROM Customer");

        int customerCount = 0;
        if(resultSet.next()) {
            customerCount = resultSet.getInt("customer_count");
        }
        return customerCount;
    }
    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT cusId from Customer order by cusId desc limit 1");

        if (resultSet.next()) {
            String cusId = resultSet.getString("cusId");
            String numericPart = cusId.replaceAll("\\D+","");
            int newCusId = Integer.parseInt(numericPart) + 1;
            return String.format("C%03d",newCusId);

        }else {
            return "C001";
        }
    }


}
