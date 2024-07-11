package lk.ijse.restaurantManagement.bo.custom;

import lk.ijse.restaurantManagement.bo.SuperBO;
import lk.ijse.restaurantManagement.dto.SalaryDTO;
import lk.ijse.restaurantManagement.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalaryBO extends SuperBO {
    public ArrayList<SalaryDTO> getAllSalaries() throws SQLException, ClassNotFoundException;


    public boolean saveSalary(SalaryDTO dto) throws SQLException;

    public Salary searchSalary(String employeeId) throws SQLException;


    public String autoGenarateSalaryId() throws SQLException;
}
