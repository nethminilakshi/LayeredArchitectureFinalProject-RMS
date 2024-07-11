package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.EmployeeDTO;
import lk.ijse.restaurantManagement.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;


    public  boolean saveEmployee(EmployeeDTO dto) throws SQLException;

    public  EmployeeDTO searchEmployee(String id) throws SQLException;

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException;

    public  boolean deleteEmployee(String employeeId) throws SQLException;

    public List<String> getEmployeeIds() throws SQLException;

    public  int getEmployeeCount() throws SQLException;
    public String autoGenarateEmployeeId() throws SQLException;
}
