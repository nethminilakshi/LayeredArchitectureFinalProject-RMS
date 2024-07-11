package lk.ijse.restaurantManagement.bo.custom.impl;

import lk.ijse.restaurantManagement.bo.custom.EmployeeBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.custom.EmployeeDAO;
import lk.ijse.restaurantManagement.dto.EmployeeDTO;
import lk.ijse.restaurantManagement.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.Employee);

    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees= employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee e : employees){
           EmployeeDTO employeeDTO = new EmployeeDTO(
                   e.getEmployeeId(),
                   e.getName(),
                   e.getAddress(),
                   e.getContact(),
                   e.getPosition(),
                   e.getBasicSalary()
           );

           employeeDTOS.add(employeeDTO);
        }
            return employeeDTOS;
    }


    public  boolean saveEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.save(new Employee(
                dto.getEmployeeId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact(),
                dto.getPosition(),
                dto.getBasicSalary()
        ));

    }

    public  EmployeeDTO searchEmployee(String id) throws SQLException {
        Employee employee = employeeDAO.search(id);
        EmployeeDTO employeeDTO = new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getAddress(),
                employee.getContact(),
                employee.getPosition(),
                employee.getBasicSalary()
        );
        return employeeDTO;
    }

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException {
        return  employeeDAO.update(new Employee(
                dto.getEmployeeId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact(),
                dto.getPosition(),
                dto.getBasicSalary()));

    }

    public  boolean deleteEmployee(String employeeId) throws SQLException {
        return employeeDAO.delete(employeeId);
    }

    public List<String> getEmployeeIds() throws SQLException {
        return employeeDAO.getIds();
    }

    public  int getEmployeeCount() throws SQLException {
        return employeeDAO.getCount();

    }
    public String autoGenarateEmployeeId() throws SQLException {
        return employeeDAO.autoGenarateId();

        }

    }

