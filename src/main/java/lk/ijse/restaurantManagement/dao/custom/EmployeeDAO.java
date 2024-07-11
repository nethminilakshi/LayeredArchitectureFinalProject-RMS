package lk.ijse.restaurantManagement.dao.custom;

import lk.ijse.restaurantManagement.dao.CrudDAO;
import lk.ijse.restaurantManagement.db.DBConnection;
import lk.ijse.restaurantManagement.dto.EmployeeDTO;
import lk.ijse.restaurantManagement.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee> {
   // public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException;

  //  public  boolean save(EmployeeDTO employeeDTO) throws SQLException;

  //  public  EmployeeDTO search(String id) throws SQLException;

   // public boolean update(EmployeeDTO employeeDTO) throws SQLException;

  //  public  boolean delete(String employeeId) throws SQLException;

  //  public  List<String>  getIds() throws SQLException;

   // public  int getCount() throws SQLException;
   // public String autoGenarateId() throws SQLException;
}
