package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.bo.BOFactory;
import lk.ijse.restaurantManagement.bo.custom.CustomerBO;
import lk.ijse.restaurantManagement.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.restaurantManagement.dto.CustomerDTO;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormController {

        @FXML
        private TableColumn<?,?> colAddress;

        @FXML
        private TableColumn<?, ?> colContact;

        @FXML
        private TableColumn<?, ?> colEmail;

        @FXML
        private TableColumn<?, ?> colId;

        @FXML
        private TableColumn<?, ?> colName;

        @FXML
        private AnchorPane root;

        @FXML
        private TableView<CustomerTM> tblCustomers;

        @FXML
        private JFXTextField txtAddress;

        @FXML
        private JFXTextField txtContact;

        @FXML
        private JFXTextField txtEmail;

        @FXML
        private JFXTextField txtGmailSend;

        @FXML
        private JFXTextField txtId;

        @FXML
        private JFXTextField txtName;

        public static String gmail="";

        private ArrayList<CustomerDTO> customerList = new ArrayList<>();

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Customer);

    public void initialize() throws ClassNotFoundException {
            try {
                autoGenarateId();
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            this.customerList = getAllCustomer();
            setCellValueFactory();
            loadCustomerTable();
        }

        private void setCellValueFactory() {
            colId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        }

   private ArrayList<CustomerDTO> getAllCustomer() throws ClassNotFoundException {
        ArrayList<CustomerDTO> customerList = null;
        try {
            customerList = customerBO.getAllCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }


        private void loadCustomerTable() {
            ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();

            for (CustomerDTO customerDTO : customerList) {
                CustomerTM customerTm = new CustomerTM(
                        customerDTO.getCusId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getContact(),
                        customerDTO.getEmail()
                );
                tmList.add(customerTm);
            }
            tblCustomers.setItems(tmList);
            System.out.println(tmList.toString());
            /*CustomerTm selectedItem = tblCustomers.getSelectionModel().getSelectedItem();*/
            /*System.out.println(selectedItem.toString());*/


            }



        @FXML
        void btnBackOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Dashboard Form");
            stage.centerOnScreen();
        }

        private void clearFields() {
            txtId.setText("");
            txtName.setText("");
            txtAddress.setText("");
            txtContact.setText("");
            txtEmail.setText("");
        }

        @FXML
        void btnClearOnAction(ActionEvent event) {
            if(isValidate()) {
                clearFields();
            }
        }

        @FXML
        void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
            if (isValidate()) {
                String contact = txtContact.getText();

                try {
                    boolean isDeleted = customerBO.deleteCustomer(contact);

                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
                clearFields();
                initialize();
            }
        }

        @FXML
        void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
            if (isValidate()){
                String cusId = txtId.getText();
                String name = txtName.getText();
                String address = txtAddress.getText();
                String contact = txtContact.getText();
                String email = txtEmail.getText();

                try {
                    boolean isSaved =customerBO.saveCustomer(new CustomerDTO(cusId,name,address,contact,email));

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
                clearFields();
                initialize();
            }}

        @FXML
        public void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
            if (isValidate()) {
                String cusId = txtId.getText();
                String name = txtName.getText();
                String address = txtAddress.getText();
                String contact = txtContact.getText();
                String email = txtEmail.getText();


                try {
                    boolean isUpdated =customerBO.updateCustomer(new CustomerDTO(cusId, name, address, contact, email));

                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
                clearFields();
                initialize();
            }
        }
        @FXML
        private void autoGenarateId() throws SQLException, ClassNotFoundException {
            txtId.setText(new CustomerDAOImpl().autoGenarateId());
        }
        @FXML
        public void txtSearchOnAction(ActionEvent event) {

            String contact = txtContact.getText();

            try {
              CustomerDTO customerDTO = customerBO.searchCustomer(contact);

                if (customerDTO != null) {
                    txtId.setText(customerDTO.getCusId());
                    txtName.setText(customerDTO.getName());
                    txtAddress.setText(customerDTO.getAddress());
                    txtContact.setText(customerDTO.getContact());
                    txtEmail.setText(customerDTO.getEmail());
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Not Found Customer").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }}


        public void tblOnClickAction(MouseEvent mouseEvent) {
            CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
            txtId.setText(selectedItem.getCusId());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtContact.setText(selectedItem.getContact());
            txtEmail.setText(selectedItem.getEmail());

        }

        public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(TextField.DESC,txtName);
        }

        public void txtCustomerContactOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(TextField.CONTACT,txtContact);
        }

        public void txtCustomerAddressOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(TextField.ADDRESS,txtContact);
        }

        public void txtCustomerEmailOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.EMAIL,txtEmail);
        }
        public boolean isValidate(){
            if(!Regex.setTextColor(TextField.DESC,txtName))return false;
            if(!Regex.setTextColor(TextField.CONTACT,txtContact))return false;
            if(!Regex.setTextColor(TextField.ADDRESS,txtAddress))return false;
            if(!Regex.setTextColor(TextField.EMAIL,txtEmail))return false;
            return true;
        }



        public void btnNextOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/mailSender_form.fxml"));
            Stage stage = new Stage();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Mail Sender Form");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            stage.show();
        }
    }





