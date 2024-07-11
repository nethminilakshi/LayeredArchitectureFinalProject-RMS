package lk.ijse.restaurantManagement.dao;


import lk.ijse.restaurantManagement.dao.custom.impl.*;


public class DAOFactory {

    private static DAOFactory daoFactory;

    public DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;

    }
    public SuperDAO getDAO(DAOFactory.DAOType daoType) {


        switch (daoType) {
            case Customer:
                return new CustomerDAOImpl();
            case Employee:
                return new EmployeeDAOImpl();
            case Item:
                return new ItemDAOImpl();
            case Order:
                return new OrderDAOImpl();
            case OrderDetails:
                return new OrderDetailsDAOImpl();
            case Pay:
                return new PaymentDAOImpl();
            case Reservation:
                return new ReservationDAOImpl();
            case ReserveDetails:
                return new ReservationDetailsDAOImpl();
            case Salary:
                return new SalaryDAOImpl();
            case Tables:
                return new TablesDAOImpl();
            default:
                return null;
        }
    }
    public enum DAOType{
        Customer,Employee,Item,Order,OrderDetails,Pay,Reservation,ReserveDetails,Salary,Tables
    }
}
