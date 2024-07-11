package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.CustomerBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.custom.CustomerDAO;
import lk.ijse.restaurantManagement.dto.CustomerDTO;
import lk.ijse.restaurantManagement.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

     CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Customer);
    public boolean saveCustomer(CustomerDTO dto) throws SQLException {

            return customerDAO.save(new Customer(
                dto.getCusId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact(),
                dto.getEmail()));
    }


    public boolean updateCustomer(CustomerDTO dto) throws SQLException {

        return customerDAO.update(new Customer(
                dto.getCusId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact(),
                dto.getEmail()));

    }
    public CustomerDTO searchCustomer(String id) throws SQLException {

               Customer customer =customerDAO.search(id);
               CustomerDTO customerDTO = new CustomerDTO(
                       customer.getCusId(),
                       customer.getName(),
                       customer.getAddress(),
                       customer.getContact(),
                       customer.getEmail());

                return customerDTO;
    }
    public boolean deleteCustomer(String contact) throws SQLException {

        return customerDAO.delete(contact);
    }
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {

        ArrayList<Customer> customers= customerDAO.getAll();
        ArrayList<CustomerDTO>customerDTOS =new ArrayList<>();
       for (Customer c : customers){
           CustomerDTO customerDTO= new CustomerDTO(
                   c.getCusId(),
                   c.getName(),
                   c.getAddress(),
                   c.getContact(),
                   c.getEmail());
           customerDTOS.add(customerDTO);
       }
        return customerDTOS;
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
    public List<String> getCustomerIds() throws SQLException { //usage for placeOrderForm

        return customerDAO.getIds();

    }
    public  int getCustomerCount() throws SQLException {return customerDAO.getCount();

    }
    public String autoGenarateCustomerId() throws SQLException {

        return customerDAO.autoGenarateId();

    }

}
