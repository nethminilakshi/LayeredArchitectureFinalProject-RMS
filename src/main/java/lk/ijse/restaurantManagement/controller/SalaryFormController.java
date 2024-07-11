package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.bo.BOFactory;
import lk.ijse.restaurantManagement.bo.custom.EmployeeBO;
import lk.ijse.restaurantManagement.bo.custom.SalaryBO;
import lk.ijse.restaurantManagement.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.restaurantManagement.db.DBConnection;
import lk.ijse.restaurantManagement.dto.SalaryDTO;
import lk.ijse.restaurantManagement.entity.Salary;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.SalaryTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SalaryFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableView<SalaryTM> tblSalary;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private DatePicker txtDate;

    @FXML
    private JFXTextField txtSalaryId;

    @FXML
    private AnchorPane root;
    private List<SalaryDTO> salaryList = new ArrayList<>();
    SalaryBO salaryBO = (SalaryBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Salary);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Employee);


    public void initialize() {
        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        this.salaryList = getAllSalaries();
        setCellValueFactory();
        loadCustomerTable();
        getEmployeeIdList();

    }

    private void getEmployeeIdList() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = employeeBO.getEmployeeIds();
            for (String id : idList) {
                obList.add(id);
            }

            cmbEmployeeId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<SalaryDTO> getAllSalaries() {

        ArrayList<SalaryDTO> salaryList = null;
        try {salaryList = salaryBO.getAllSalaries();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return salaryList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadCustomerTable() {
        ObservableList<SalaryTM> tmList = FXCollections.observableArrayList();

        for (SalaryDTO salaryDTO : salaryList) {
            SalaryTM salaryTm = new SalaryTM(
                    salaryDTO.getSalaryId(),
                    salaryDTO.getEmployeeId(),
                    salaryDTO.getAmount(),
                    salaryDTO.getDate()
            );
            tmList.add(salaryTm);
        }
        tblSalary.setItems(tmList);
        System.out.println(tmList.toString());

    }

   /* public void tblOnClickAction(MouseEvent mouseEvent) {
        SalaryTM selectedItem = tblSalary.getSelectionModel().getSelectedItem();
        txtSalaryId.setText(selectedItem.getSalaryId());
        cmbEmployeeId.setValue(selectedItem.getEmployeeId());
        txtAmount.setText(String.valueOf(selectedItem.getAmount()));
        txtDate.setValue(LocalDate.parse(selectedItem.getDate()));

    }

    */
    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
        if (isValidate()) {
            clearFields();
        }
    }
    @FXML
    private void clearFields() {
        txtSalaryId.setText("");
        cmbEmployeeId.setValue("");
        txtAmount.setText("");
        txtDate.setValue(LocalDate.parse(""));
    }

    @FXML
    void btnSaveOnAction(ActionEvent actionEvent) {
        if (isValidate()) {
            String salaryId = txtSalaryId.getText();
            String employeeId = cmbEmployeeId.getValue();
            double amount = Double.parseDouble(txtAmount.getText());
            String date = String.valueOf(txtDate.getValue());

            try {
                boolean isSaved = salaryBO.saveSalary(new SalaryDTO(salaryId, employeeId, amount, date));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Salary paid!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
            initialize();
        }
    }
    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }
    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtSalaryId.setText(new SalaryDAOImpl().autoGenarateId());
    }

    public void cmbIdOnAction(ActionEvent actionEvent) {

        String employeeId  = cmbEmployeeId.getValue();
        try {
            Salary salaryDTO = salaryBO.searchSalary(employeeId);
            if (salaryDTO != null) {
                txtSalaryId.setText(salaryDTO.getSalaryId());
               // txtAmount.setText(String.valueOf(salaryDTO.getAmount()));
                txtDate.setValue(LocalDate.parse(salaryDTO.getDate()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReceiptOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        if (isValidate()){
            JasperDesign jasperDesign =
                    JRXmlLoader.load("src/main/resources/reports/salaryPayments.jrxml");
            JasperReport jasperReport =
                    JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> data = new HashMap<>();
            data.put("salaryId",txtSalaryId.getText());
            data.put("employeeId",cmbEmployeeId.getValue());

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            data,
                            DBConnection.getDbConnection().getConnection());

            JasperViewer.viewReport(jasperPrint,false);
        }
    }
    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.SALARY,txtAmount);
    }

    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.AMOUNT,txtAmount))return false;
        return true;
    }
}

