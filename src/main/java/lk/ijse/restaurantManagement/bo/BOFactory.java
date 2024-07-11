package lk.ijse.restaurantManagement.bo;

import lk.ijse.restaurantManagement.bo.custom.impl.*;


public class BOFactory {

    private static BOFactory boFactory;

    public BOFactory(){}

    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory(): boFactory;
    }
    public SuperBO getBO(BOFactory.DAOType daoType){

        switch (daoType){
            case Customer:
                return new CustomerBOImpl();
            case Employee:
                return new EmployeeBOImpl();
            case Item:
                return new ItemBOImpl();
            case Pay:
                return new PaymentBOImpl();
            case Po:
                return new PurchaseOrderBOImpl();
            case Reservation:
                return new ReservationBOImpl();
            case ReserveDetails:
                return new ReservationDetailsBOImpl();
            case Salary:
                return new SalaryBOImpl();
            case Tables:
                return new TablesBOImpl();
            default:
                return null;
        }
    }

    public enum DAOType{
        Customer,Employee,Item,Pay,Po,Reservation,ReserveDetails,Salary,Tables
    }
}
