package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.bo.BOFactory;
import lk.ijse.restaurantManagement.bo.custom.EmployeeBO;
import lk.ijse.restaurantManagement.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.restaurantManagement.dto.EmployeeDTO;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.EmployeeTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class EmployeeFormController {

        @FXML
        private ComboBox<String> cmbPosition;

        @FXML
        private TableColumn<?, ?> colAddress;

        @FXML
        private TableColumn<?, ?> colContact;

        @FXML
        private TableColumn<?, ?> colId;

        @FXML
        private TableColumn<?, ?> colName;

        @FXML
        private TableColumn<?, ?> colPosition;

        @FXML
        private TableColumn<?, ?> colBasicSalary;

        @FXML
        private AnchorPane root;

        @FXML
        private TableView<EmployeeTM> tblEmployee;

        @FXML
        private JFXTextField txtAddress;

        @FXML
        private JFXTextField txtContact;

        @FXML
        private JFXTextField txtId;

        @FXML
        private JFXTextField txtName;

        @FXML
        private JFXTextField txtSalary;
        private List<EmployeeDTO> employeeList = new ArrayList<>();

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Employee);


    public void initialize() throws ClassNotFoundException {
            try {
                autoGenarateId();
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            this.employeeList = getAllEmployees();
            getPositionList();
            setCellValueFactory();
            loadEmployeeTable();
        }
        private String[] positionList={"Chef","Kitchen Helper", "Server"};

        public void getPositionList(){
            List<String> positionlist = new ArrayList<>();
            for(String data: positionList){
                positionlist.add(data);
            }
            ObservableList<String> obList= FXCollections.observableArrayList(positionlist);
            cmbPosition.setItems(obList);

        }

        private void loadEmployeeTable() {
            ObservableList<EmployeeTM> tmList = FXCollections.observableArrayList();

            for (EmployeeDTO employeeDTO : employeeList) {
                EmployeeTM employeeTm = new EmployeeTM(
                        employeeDTO.getEmployeeId(),
                        employeeDTO.getName(),
                        employeeDTO.getAddress(),
                        employeeDTO.getContact(),
                        employeeDTO.getPosition(),
                        employeeDTO.getBasicSalary()
                );

                tmList.add(employeeTm);
            }
            tblEmployee.setItems(tmList);
            EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
            System.out.println("selectedItem = " + selectedItem);
        }

        private List<EmployeeDTO> getAllEmployees() throws ClassNotFoundException {
            List<EmployeeDTO> employeeList = null;
            try {
                employeeList = employeeBO.getAllEmployees();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return employeeList;
        }

        private void setCellValueFactory() {
            colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
            colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));

        }

        public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(rootNode));
            stage.setTitle("Dashboard Form");
            stage.centerOnScreen();
        }

        public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
            if(isValidate()){
                String employeeId= txtId.getText();
                String name = txtName.getText();
                String address = txtAddress.getText();
                String contact = txtContact.getText();
                String position = String.valueOf(cmbPosition.getValue());
                String basicSalary = txtSalary.getText();

                try {
                    boolean isSaved = employeeBO.saveEmployee(new EmployeeDTO(employeeId, name,address,contact,position,basicSalary));
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
                clearFields();
                initialize();
//        now we should persist our customer model
            }}

        public void txtSearchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {

            String contact = txtContact.getText();

            try {
                    EmployeeDTO employeeDTO = employeeBO.searchEmployee(contact);

                if (employeeDTO != null) {
                    txtId.setText(employeeDTO.getEmployeeId());
                    txtName.setText(employeeDTO.getName());
                    txtAddress.setText(employeeDTO.getAddress());
                    txtContact.setText(employeeDTO.getContact());
                    cmbPosition.setValue(employeeDTO.getPosition());
                    txtSalary.setText(employeeDTO.getBasicSalary());

                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Not Found Customer").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            initialize();
        }


        public void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
            if (isValidate()){
                String employeeId=txtId.getText();
                String name = txtName.getText();
                String address = txtAddress.getText();
                String contact = txtContact.getText();
                String position = String.valueOf(cmbPosition.getValue());
                String basicSalary = txtSalary.getText();



                try {
                    boolean isUpdated = employeeBO.updateEmployee(new EmployeeDTO(employeeId,name,address,contact,position,basicSalary));
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

                initialize();
            }
        }

        private void clearFields() {
            txtId.setText("");
            txtName.setText("");
            txtAddress.setText("");
            txtContact.setText("");
            cmbPosition.setValue("");
            txtSalary.setText("");
        }

        public void btnClearOnAction(ActionEvent actionEvent) {
            if (isValidate()) {
                clearFields();
            }
        }
        public void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
            if (isValidate()){
                String contact= txtContact.getText();

                try {
                        boolean isDeleted = employeeBO.deleteEmployee(contact);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
                clearFields();
                initialize();
            }
        }

        public void tblClickOnAction(MouseEvent mouseEvent) {
            EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
            txtId.setText(selectedItem.getEmployeeId());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtContact.setText(selectedItem.getContact());
            cmbPosition.setValue(selectedItem.getPosition());
            txtSalary.setText(selectedItem.getBasicSalary());
        }
        @FXML
        private void autoGenarateId() throws SQLException, ClassNotFoundException {
            txtId.setText(new EmployeeDAOImpl().autoGenarateId());
        }


        public void txtEmployeeContactOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(TextField.CONTACT,txtContact);
        }

        public void txtEmployeeAddressOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(TextField.ADDRESS,txtAddress);
        }

        public void txtEmployeeNameOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(TextField.NAME,txtName);
        }

        public boolean isValidate(){
            if(!Regex.setTextColor(TextField.NAME,txtName))return false;
            if(!Regex.setTextColor(TextField.CONTACT,txtContact))return false;
            if(!Regex.setTextColor(TextField.ADDRESS,txtAddress))return false;

            return true;
        }
    }


