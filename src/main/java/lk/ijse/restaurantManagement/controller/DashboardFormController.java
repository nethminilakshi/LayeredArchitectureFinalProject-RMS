package lk.ijse.restaurantManagement.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.dao.custom.CustomerDAO;
import lk.ijse.restaurantManagement.dao.custom.EmployeeDAO;
import lk.ijse.restaurantManagement.dao.custom.ItemDAO;
import lk.ijse.restaurantManagement.dao.custom.OrderDAO;
import lk.ijse.restaurantManagement.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.restaurantManagement.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.restaurantManagement.dao.custom.impl.ItemDAOImpl;
import lk.ijse.restaurantManagement.dao.custom.impl.OrderDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {

        @FXML
        private AnchorPane bodyPane;

        @FXML
        private AnchorPane root;

        @FXML
        private Label lblCustomerCount;

        @FXML
        private Label lblEmployeeCount;

        @FXML
        private Label lblItemCount;
        private int customerCount;
        private int itemCount;
        private int employeeCount;

        @FXML
        private Label lblDate;

        @FXML
        private Label lblTime;

        private volatile boolean stop = false;


        @FXML
        private BarChart<String, Number> barChartOrders;

        public void initialize() throws SQLException {

            timeNow();
            LocalDate date = LocalDate.now();
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("EEEE, MMM dd");
            String formattedDate = date.format(dateformatter);
            lblDate.setText(formattedDate);

            try {
                CustomerDAO customerDAO = new CustomerDAOImpl();
                customerCount = customerDAO.getCount();

                EmployeeDAO employeeDAO = new EmployeeDAOImpl();
                employeeCount = employeeDAO.getCount();

                ItemDAO itemDAO = new ItemDAOImpl();
                itemCount = itemDAO.getCount();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            setCustomerCount(customerCount);
            setItemCount(itemCount);
            setEmployeeCount(employeeCount);
            OrderDAO orderDAO = new OrderDAOImpl();
            orderDAO.OrdersCount(barChartOrders);
        }


        public void timeNow(){
            Thread thread = new Thread(()->{
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                while (!stop){
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    final String timenow = sdf.format(new Date());
                    Platform.runLater(()->{
                        lblTime.setText(timenow);
                    });
                }
            });
            thread.start();
        }

        private void setItemCount(int itemCount) {
            lblItemCount.setText(String.valueOf(itemCount));
        }

        private void setCustomerCount(int customerCount) {
            lblCustomerCount.setText(String.valueOf(customerCount));
        }


        private void setEmployeeCount(int employeeCount) {
            lblEmployeeCount.setText(String.valueOf(employeeCount));
        }

        public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

            Scene scene = new Scene(rootNode);

            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login Form");
        }


    }


