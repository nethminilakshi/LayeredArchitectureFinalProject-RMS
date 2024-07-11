package lk.ijse.restaurantManagement.dao;

import lk.ijse.restaurantManagement.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    public boolean save(T dto) throws SQLException;
    public  boolean update(T dto) throws SQLException;
    public T search(String id) throws SQLException;
    public  boolean delete(String contact) throws SQLException;
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    public List<String>  getIds() throws SQLException;
    public  int getCount() throws SQLException;
    public String autoGenarateId() throws SQLException;
}
