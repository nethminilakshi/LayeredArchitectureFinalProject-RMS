package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.SalaryBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.SalaryDAO;
import lk.ijse.restaurantManagement.dto.SalaryDTO;
import lk.ijse.restaurantManagement.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryBOImpl implements SalaryBO {

    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Salary);
    public ArrayList<SalaryDTO> getAllSalaries() throws SQLException, ClassNotFoundException {

        ArrayList<Salary> salaries = salaryDAO.getAll();
        ArrayList<SalaryDTO> salaryDTOS = new ArrayList<>();
            for (Salary s : salaries){
                SalaryDTO salaryDTO = new SalaryDTO(
                s.getSalaryId(),
                s.getEmployeeId(),
                s.getAmount(),
                s.getDate());

                salaryDTOS.add(salaryDTO);
            }
        return salaryDTOS;
    }


    public boolean saveSalary(SalaryDTO dto) throws SQLException {

       return salaryDAO.save(new Salary(
               dto.getSalaryId(),
               dto.getEmployeeId(),
               dto.getAmount(),
               dto.getDate()
       ));
    }

    public Salary searchSalary(String employeeId) throws SQLException {

       return salaryDAO.search(employeeId);
    }


    public String autoGenarateSalaryId() throws SQLException {


        return salaryDAO.autoGenarateId();
    }
}
