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
import lk.ijse.restaurantManagement.bo.custom.CustomerBO;
import lk.ijse.restaurantManagement.bo.custom.ItemBO;
import lk.ijse.restaurantManagement.bo.custom.PurchaseOrderBO;
import lk.ijse.restaurantManagement.dao.custom.impl.OrderDAOImpl;
import lk.ijse.restaurantManagement.db.DBConnection;
import lk.ijse.restaurantManagement.dto.*;
import lk.ijse.restaurantManagement.entity.*;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.OrderDetailTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController {
        @FXML
        private AnchorPane root;

        @FXML
        private JFXButton btnAddToCart;

        @FXML
        private JFXButton btnPlaceOrder;

        @FXML
        private TableColumn<?, ?> colAction;

        @FXML
        private TableColumn<?, ?> colDescription;

        @FXML
        private TableColumn<?, ?> colItemCode;

        @FXML
        private TableColumn<?, ?> colQty;

        @FXML
        private TableColumn<?, ?> colTotal;

        @FXML
        private TableColumn<?, ?> colUnitPrice;
        @FXML
        private TableColumn<?, ?> coldate;

        @FXML
        private TableView<OrderDetailTM> tblOrderDetails;

        @FXML
        private JFXTextField txtCode;

        @FXML
        private JFXTextField txtContact;

        @FXML
        private JFXTextField txtCustomerName;

        @FXML
        private JFXTextField txtDate;

        @FXML
        private JFXTextField txtDescription;

        @FXML
        private JFXTextField txtId;

        @FXML
        private JFXTextField txtNetTotal;

        @FXML
        private JFXTextField txtOrderId;

        @FXML
        private JFXTextField txtQty;

        @FXML
        private JFXTextField txtQtyOnHand;

        @FXML
        private JFXTextField txtUnitPrice;

        @FXML
        private ComboBox<String> cmbOrderType;
        private final ObservableList<OrderDetailTM> cartList = FXCollections.observableArrayList();
        private double netTotal = 0;
        ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Item);
        PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Po);
        CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Customer);


    public void initialize() {
            setCellValueFactory();
            //  loadNextOrderId();
            setDate();
            loadTable();
            getOrderList();

            try {
                autoGenarateId();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }

    private void autoGenarateId() throws SQLException {
        txtOrderId.setText(new OrderDAOImpl().autoGenarateId());
    }

    private String[] typeList={"takeAway","dineIn"};
        public void getOrderList(){
            List<String> typelist = new ArrayList<>();
            for(String data: typeList){
                typelist.add(data);
            }
            ObservableList<String> obList= FXCollections.observableArrayList(typelist);
            cmbOrderType.setItems(obList);
        }


    private void loadTable() {
        ObservableList<OrderDetailTM> tmList = FXCollections.observableArrayList();

        for (OrderDetailTM od : cartList) {
            OrderDetailTM odTm = new OrderDetailTM(
                    od.getCode(),
                    od.getDescription(),
                    od.getQty(),
                    od.getUnitPrice(),
                    od.getTotal(),
                    od.getDate(),
                    od.getBtnRemove()
            );


            tmList.add(odTm);
        }
        tblOrderDetails.setItems(tmList);
        OrderDetailTM selectedItem = tblOrderDetails.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

        private void setDate() {
            String now = String.valueOf(LocalDate.now());
            txtDate.setText(now);

        }

        private String nextId(String currentId) {
            if (currentId != null) {
                String[] split = currentId.split("O");
//            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
                int id = Integer.parseInt(split[1]);    //2
                return "O" + ++id;

            }
            return "O1";
        }



        private void setCellValueFactory() {
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
        }

        @FXML
        void btnAddToCartOnAction(ActionEvent event) {
            if (isValidate()) {
                String id = txtCode.getText();
                String description = txtDescription.getText();
                int qty = Integer.parseInt(txtQty.getText());
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                double total = qty * unitPrice;
                String date = txtDate.getText();
                JFXButton btnRemove = new JFXButton("remove");
                btnRemove.setCursor(Cursor.HAND);

                btnRemove.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        int selectedIndex = tblOrderDetails.getSelectionModel().getSelectedIndex();
                        cartList.remove(selectedIndex);

                        tblOrderDetails.refresh();
                        calculateNetTotal();
                    }
                });

                for (int i = 0; i < tblOrderDetails.getItems().size(); i++) {
                    if (id.equals(colItemCode.getCellData(i))) {
                        qty += cartList.get(i).getQty();
                        total = unitPrice * qty;

                        cartList.get(i).setQty(qty);
                        cartList.get(i).setTotal(total);

                        tblOrderDetails.refresh();
                        calculateNetTotal();
                        txtQty.setText("");
                        return;
                    }
                    Clear();
                }

                OrderDetailTM orderDetailTM = new OrderDetailTM(id, description, qty, unitPrice, total, date, btnRemove);

                cartList.add(orderDetailTM);

                tblOrderDetails.setItems(cartList);
                txtQty.setText("");
                calculateNetTotal();
            }

        }

        private void calculateNetTotal() {
            netTotal = 0;
            for (int i = 0; i < tblOrderDetails.getItems().size(); i++) {
                netTotal += (double) colTotal.getCellData(i);
            }
            txtNetTotal.setText(String.valueOf(netTotal));
        }
        @FXML
        void btnBackOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Dashboard Form");
            stage.centerOnScreen();
        }

        @FXML
       public void btnPlaceOrderOnAction(ActionEvent event) {
            if (isValidate()){
                String orderId = txtOrderId.getText();
                String orderType= String.valueOf(cmbOrderType.getValue());
                String cusId = txtId.getText();
                String date = String.valueOf(Date.valueOf(LocalDate.now()));

                var order = new OrderDTO(orderId,orderType, cusId, date);

                List<OrderDetail> odList = new ArrayList<>();
                for (int i = 0; i < tblOrderDetails.getItems().size(); i++) {
                    OrderDetailTM tm = cartList.get(i);

                    OrderDetail od = new OrderDetail(
                            orderId,
                            tm.getCode(),
                            tm.getQty(),
                            tm.getUnitPrice()
                    );
                    odList.add(od);
                }

                try {
                    boolean isPlaced = saveOrder(orderId,orderType,cusId,date, (List<OrderDetailDTO>) order);
                    if(isPlaced) {
                        new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "order not placed!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }


            }}

   public boolean saveOrder(String orderId, String orderType,String cusId, String date, List<OrderDetailDTO> orderDetails) throws SQLException {

        return purchaseOrderBO.placeOrder(orderId, orderType, cusId,date, orderDetails);
    }

            @FXML
            void txtQtyOnAction (ActionEvent event){
                btnAddToCartOnAction(event);

            }

            private void clearFields () {
                cmbOrderType.setValue("");
                txtCode.setText("");
                txtDescription.setText("");
                txtUnitPrice.setText("");
                txtQtyOnHand.setText("");
                txtContact.setText("");
                txtId.setText("");
                txtCustomerName.setText("");
                txtNetTotal.setText("");
            }

            private void Clear () {

                txtCode.setText("");
                txtDescription.setText("");
                txtUnitPrice.setText("");
                txtQtyOnHand.setText("");
            }

            public void txtDescOnKeyReleased (KeyEvent keyEvent){
                Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.DESC, txtDescription);
            }

            public boolean isValidate () {
                if (!Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.DESC, txtDescription)) return false;
                if (!Regex.setTextColor(TextField.CONTACT, txtContact)) return false;
                return true;
            }


    public void btnClearOnAction(ActionEvent actionEvent) {
        if (isValidate()) {
            clearFields();
        }
    }

    public void btnReceiptOnAction(ActionEvent actionEvent) throws SQLException, JRException {
        if (isValidate()) {
            JasperDesign jasperDesign =
                    JRXmlLoader.load("src/main/resources/reports/order_details.jrxml");
            JasperReport jasperReport =
                    JasperCompileManager.compileReport(jasperDesign);


            Map<String, Object> data = new HashMap<>();
            data.put("orderId", txtOrderId.getText());
            data.put("unitPrice", txtUnitPrice.getText());

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            data,
                            DBConnection.getDbConnection().getConnection());

            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    public void btnGetReceipt(ActionEvent actionEvent) throws SQLException, JRException {
        if (isValidate()) {
            JasperDesign jasperDesign =
                    JRXmlLoader.load("src/main/resources/reports/CustomerReceipt.jrxml");
            JasperReport jasperReport =
                    JasperCompileManager.compileReport(jasperDesign);


            Map<String, Object> data = new HashMap<>();
            data.put("orderId", txtOrderId.getText());
            data.put("qty", txtQty.getText());

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            data,
                            DBConnection.getDbConnection().getConnection());

            JasperViewer.viewReport(jasperPrint, false);
        }

    }

    public void txtAContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.CONTACT, txtContact);

    }

    public void btnsearchOnAction(ActionEvent actionEvent) {
        String contact = txtContact.getText();

        try {
            CustomerDTO customerDTO = customerBO.searchCustomer(contact);
            txtId.setText(customerDTO.getCusId());
            txtCustomerName.setText(customerDTO.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    public void searchOnItemCode(ActionEvent actionEvent) {
                String Id = txtCode.getText();

                try {
                    ItemDTO itemDTO = itemBO.searchItem(Id);
                    if (itemDTO != null) {
                        txtCode.setText(itemDTO.getId());
                        txtDescription.setText(itemDTO.getDescription());
                        txtUnitPrice.setText(itemDTO.getUnitPrice());
                        txtQtyOnHand.setText(itemDTO.getQtyOnHand());

                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Not Found Item ").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
    }


}