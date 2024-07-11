package lk.ijse.restaurantManagement.dao.custom.impl;

import lk.ijse.restaurantManagement.dao.SQLUtil;
import lk.ijse.restaurantManagement.dao.custom.ReservationDAO;
import lk.ijse.restaurantManagement.entity.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    public  boolean save(Reservation entity) throws SQLException {

        return SQLUtil.execute("INSERT INTO Reservation VALUES(?, ?, ?, ?, ?, ?)",
                entity.getReservationId(),
                entity.getDescription(),
                entity.getCusId(),
                entity.getDate(),
                entity.getTime(),
                entity.getStatus());
    }

    @Override
    public boolean update(Reservation dto) throws SQLException {
        return false;
    }

    public Reservation search(String Id) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Reservation WHERE cusId = ?", Id);

        Reservation reservation = null;

        if (resultSet.next()) {
            String reservationId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String cusId = resultSet.getString(3);
            String date = resultSet.getString(4);
            String time = resultSet.getString(5);
            String status = resultSet.getString(6);


            reservation = new Reservation(reservationId, description, cusId,date,time,status);
        }
        return reservation;
    }

    @Override
    public boolean delete(String contact) throws SQLException {
        return false;
    }

    public ArrayList<Reservation> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Reservation");

        ArrayList<Reservation> reservationList = new ArrayList<>();
        while (resultSet.next()) {
            String reservationId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String cusId = resultSet.getString(3);
            String date = resultSet.getString(4);
            String time = resultSet.getString(5);
            String status = resultSet.getString(6);

            Reservation reservation = new Reservation(reservationId, description, cusId, date,time, status);
            reservationList.add(reservation);
        }
        return reservationList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    public String autoGenarateId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT reservationId from Reservation order by reservationId desc limit 1");

        if (resultSet.next()) {
            String reserveId = resultSet.getString("reservationId");
            String numericPart = reserveId.replaceAll("\\D+", "");
            int newReservationId = Integer.parseInt(numericPart) + 1;
            return String.format("R%03d", newReservationId);

        } else {
            return "R001";
        }
    }
}
