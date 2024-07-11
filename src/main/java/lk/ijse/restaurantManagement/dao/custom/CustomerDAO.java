package lk.ijse.restaurantManagement.dao.custom;

import lk.ijse.restaurantManagement.dao.CrudDAO;
import lk.ijse.restaurantManagement.dto.CustomerDTO;
import lk.ijse.restaurantManagement.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {

  /*  public boolean save(Customer customer) throws SQLException;
    public  boolean update(Customer customer) throws SQLException;
    public CustomerDTO searchByContact(String id) throws SQLException;
    public  boolean delete(String contact) throws SQLException;
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException;

    public  List<String>  getIds() throws SQLException;
    public  int getCustomerCount() throws SQLException;
    public String autoGenarateCustomerId() throws SQLException;

    */
}
