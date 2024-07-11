package lk.ijse.restaurantManagement.view.tdm;

public class EmployeeTM {
    private String employeeId;
    private String name;
    private String address;
    private String contact;
    private String position;
    private String basicSalary;

    public EmployeeTM() {
    }

    public EmployeeTM(String employeeId, String name, String address, String contact, String position, String basicSalary){
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.position = position;
        this.basicSalary = basicSalary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", position='" + position + '\'' +
                ", basicSalary='" + basicSalary + '\'' +
                '}';
    }
    }

