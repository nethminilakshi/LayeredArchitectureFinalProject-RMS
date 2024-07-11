package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.bo.BOFactory;
import lk.ijse.restaurantManagement.bo.custom.PaymentBO;
import lk.ijse.restaurantManagement.dao.custom.OrderDAO;
import lk.ijse.restaurantManagement.dao.custom.impl.OrderDAOImpl;
import lk.ijse.restaurantManagement.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.restaurantManagement.dto.PaymentDTO;
import lk.ijse.restaurantManagement.entity.Order;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.PaymentTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentFormController {
    @FXML
    private ComboBox<String> cmbPayMethod;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPayMethod;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtCusId;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtOrderId;
    @FXML
    private JFXButton btnAdd;

    private ArrayList<PaymentDTO> paymentList=new ArrayList<>();
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Pay);

    OrderDAO orderDAO = new OrderDAOImpl();


    public void initialize() throws ClassNotFoundException {
        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        this.paymentList = getAllPayments();
        getPayMethod();
        setCellValueFactory();
        loadItemTable();

    }

    private String[] payMethodList={"Cash","Card"};

    private void getPayMethod() {
        List<String> Methodlist = new ArrayList<>();
        for(String data: payMethodList){
            Methodlist.add(data);
        }
        ObservableList<String> obList= FXCollections.observableArrayList(Methodlist);
        cmbPayMethod.setItems(obList);
    }


    private ArrayList<PaymentDTO> getAllPayments() throws ClassNotFoundException {
        ArrayList<PaymentDTO> paymentList= null;
        try {
            paymentList = paymentBO.getAllPayments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    private void loadItemTable() {
        ObservableList<PaymentTM> tmList = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentList) {
            PaymentTM paymentTm = new PaymentTM(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getCusId(),
                    paymentDTO.getOrderId(),
                    paymentDTO.getPayMethod(),
                    paymentDTO.getAmount()
            );
            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        PaymentTM selectedItem = tblPayment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPayMethod.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        // colPaymentaction.setCellValueFactory(new PropertyValueFactory<>("PaymentAction"));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (isValidate()) {
            String paymentId = txtId.getText();
            String cusId = txtCusId.getText();
            String orderId = txtOrderId.getText();
            String payMethod = cmbPayMethod.getValue();
            Double amount = Double.valueOf(txtAmount.getText());


            btnAdd.setCursor(Cursor.HAND);

            btnAdd.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to pay?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {

                    try {
                        boolean isSaved = paymentBO.savePayment(new PaymentDTO(paymentId, cusId, orderId, payMethod, amount));
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "paid!").show();
                            //clearFields();
                        }
                    } catch (SQLException exception) {
                        new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
                    }
                    clearFields();
                   // initialize();
                }

            })
            ;
        }
    }

    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtId.setText(new PaymentDAOImpl().autoGenarateId());
    }
    @FXML
    private void clearFields() {
        txtId.setText("");
        txtCusId.setText("");
        txtOrderId.setText("");
        txtAmount.setText("");
    }
    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
        if (isValidate()) {
            clearFields();
        }
    }

    public void searchOnAction(ActionEvent event) throws ClassNotFoundException {

        String orderId = txtOrderId.getText();

        try {
            Order orderDTO = orderDAO.search(orderId);

            txtOrderId.setText(orderDTO.getOrderId());
            txtCusId.setText(orderDTO.getCusId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    public void txtAmountOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.AMOUNT,txtAmount);
    }
    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.AMOUNT,txtAmount))return false;
        return true;
    }
}
