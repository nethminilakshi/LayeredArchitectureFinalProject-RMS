package lk.ijse.restaurantManagement.dto;

import java.io.Serializable;

public class SalaryDTO implements Serializable {
    private String salaryId;
    private String employeeId;
    private double amount;
    private String date;

    public SalaryDTO() {
    }

    public SalaryDTO(String salaryId, String employeeId, double amount, String date) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.amount = amount;
        this.date = date;
    }

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalaryDTO{" +
                "salaryId='" + salaryId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                '}';
    }
}
