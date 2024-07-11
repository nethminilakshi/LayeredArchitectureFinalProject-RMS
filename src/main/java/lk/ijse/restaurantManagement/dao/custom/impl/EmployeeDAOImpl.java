package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.EmployeeDAO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    public ArrayList<Employee> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Employee");

        ArrayList<Employee> employees=new ArrayList<>();
        while (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String position = resultSet.getString(5);
            String basicSalary = resultSet.getString(6);

            Employee employee = new Employee(employeeId, name, address, contact, position, basicSalary);
            employees.add(employee);
        }
        return employees;
    }


    public  boolean save(Employee entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?)",
                entity.getEmployeeId(),
                entity.getName(),
                entity.getAddress(),
                entity.getContact(),
                entity.getPosition(),
                entity.getBasicSalary());
    }

    public Employee search(String id) throws SQLException {

        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM Employee WHERE contact = ?", id);
        Employee employee = null;

        if (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String position = resultSet.getString(5);
            String basicSalary = resultSet.getString(6);


            employee = new Employee(employeeId, name, address, contact, position, basicSalary);
        }
        return employee;
    }

    public boolean update(Employee entity) throws SQLException {

        return SQLUtil.execute( "UPDATE Employee SET employeeId = ?, name = ?, address = ?,position = ?, basicSalary = ? WHERE contact = ?",
                entity.getEmployeeId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPosition(),
                entity.getBasicSalary(),
                entity.getContact());
    }

    public  boolean delete(String employeeId) throws SQLException {
        return SQLUtil.execute( "DELETE FROM Employee WHERE contact = ?", employeeId);
    }

    public  List<String>  getIds() throws SQLException {

        List<String> idList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT employeeId FROM Employee");

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public  int getCount() throws SQLException {

        ResultSet resultSet = SQLUtil.execute( "SELECT COUNT(*) AS employee_count FROM Employee");

        int employeeCount = 0;
        if(resultSet.next()) {
            employeeCount = resultSet.getInt("employee_count");
        }
        return employeeCount;
    }
    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT employeeId from Employee order by employeeId desc limit 1");

        if (resultSet.next()) {
            String employeeId = resultSet.getString("employeeId");
            String numericPart = employeeId.replaceAll("\\D+", "");
            int newEmployeeId = Integer.parseInt(numericPart) + 1;
            return String.format("E%03d", newEmployeeId);

        } else {
            return "E001";

        }
    }
}
