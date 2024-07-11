package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.CustomerDTO;
import lk.ijse.restaurantManagement.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public boolean saveCustomer(CustomerDTO dto) throws SQLException;


    public boolean updateCustomer(CustomerDTO dto) throws SQLException;
    public CustomerDTO searchCustomer(String id) throws SQLException;
    public boolean deleteCustomer(String contact) throws SQLException;
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    public List<String> getCustomerIds() throws SQLException;
    public  int getCustomerCount() throws SQLException;
    public String autoGenarateCustomerId() throws SQLException;
}
