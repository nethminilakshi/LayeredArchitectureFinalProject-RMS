package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.SalaryDAO;
import lk.ijse.restaurantManagement.entity.Customer;
import lk.ijse.restaurantManagement.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {

    public ArrayList<Salary> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Salary");
        ArrayList<Salary> salaryList = new ArrayList<>();

        while (resultSet.next()) {
            String salaryId = resultSet.getString(1);
            String employeeId = resultSet.getString(2);
            double amount = resultSet.getDouble(3);
            String date = resultSet.getString(4);

            Salary salary = new Salary(salaryId, employeeId, amount, date);
            salaryList.add(salary);
        }
        return salaryList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    public boolean save(Salary entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Salary VALUES(?, ?, ?, ?)",
                entity.getSalaryId(),
                entity.getEmployeeId(),
                entity.getAmount(),
                entity.getDate());
    }

    @Override
    public boolean update(Salary dto) throws SQLException {
        return false;
    }

    public Salary search(String employeeId) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Salary WHERE employeeId = ?", employeeId);

        Salary salary = null;

        if (resultSet.next()) {
            String salaryId = resultSet.getString(1);
            String EmployeeId = resultSet.getString(2);
            double amount = resultSet.getDouble(3);
            String date = resultSet.getString(4);


            salary = new Salary(salaryId, EmployeeId, amount, date);
        }
        return salary;
    }

    @Override
    public boolean delete(String contact) throws SQLException {
        return false;
    }


    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT salaryId from Salary order by salaryId desc limit 1");

        if (resultSet.next()) {
            String salaryId = resultSet.getString("salaryId");
            String numericPart = salaryId.replaceAll("\\D+","");
            int newSalaryId = Integer.parseInt(numericPart) + 1;
            return String.format("Sal%03d",newSalaryId);

        }else {
            return "Sal001";

        }
    }
}
