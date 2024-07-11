package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import lk.ijse.restaurantManagement.bo.BOFactory;
import lk.ijse.restaurantManagement.bo.custom.TablesBO;
import lk.ijse.restaurantManagement.dao.custom.impl.TablesDAOImpl;
import lk.ijse.restaurantManagement.dto.TablesDTO;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.TablesTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesFormController {
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colSeats;

    @FXML
    private TableColumn<?, ?> colTableId;

    @FXML
    private TableColumn<?, ?> colTables;

    @FXML
    private Label lblFamilyTbl1;

    @FXML
    private Label lblFamilyTbl2;

    @FXML
    private Label lblSingleTbl;

    @FXML
    private Label lbloutdoorTbl;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<TablesTM> tblTables;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtTableId;

    @FXML
    private JFXTextField txtTables;

    @FXML
    private JFXTextField txtseats;

    private List<TablesDTO> tablesList = new ArrayList<>();
    private String[] labelList = {"lblFamilyTbl1","lblFamilyTbl2","lblSingleTbl","lbloutdoorTbl"};
    TablesBO tablesBO = (TablesBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Tables);

    private void initialize() throws ClassNotFoundException {
        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        this.tablesList=getAllTables();
        setCellValueFactory();
        loadTablesTable();

    }


    private List<TablesDTO> getAllTables() throws ClassNotFoundException {
        ArrayList<TablesDTO> tablesList = null;
        try {
            tablesList = tablesBO.getAllTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tablesList;
    }


    private void loadTablesTable() {
        ObservableList<TablesTM> tmList = FXCollections.observableArrayList();

        for (TablesDTO tablesDTO : tablesList) {
            TablesTM tablesTM = new TablesTM(
                    tablesDTO.getTableId(),
                    tablesDTO.getDescription(),
                    tablesDTO.getNoOfTables(),
                    tablesDTO.getNoOfSeats()
            );
            tmList.add(tablesTM);
        }
        tblTables.setItems(tmList);
        System.out.println(tmList.toString());
    }

    private void setCellValueFactory() {
        colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colTables.setCellValueFactory(new PropertyValueFactory<>("noOfTables"));
        colSeats.setCellValueFactory(new PropertyValueFactory<>("noOfSeats"));
    }

    public void addOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        if (isValidate()){
            String tableId = txtTableId.getText();

            try {
                boolean isDeleted = tablesBO.deleteTables(tableId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Table deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
           initialize();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage)root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        if (isValidate()) {
            String tableId = txtTableId.getText();
            String description = txtDescription.getText();
            int noOfTables = Integer.parseInt(txtTables.getText());
            int noOfSeats = Integer.parseInt(txtseats.getText());


            try {
                boolean isUpdated = tablesBO.updateTables(new TablesDTO(tableId, description, noOfTables, noOfSeats));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Table updated!").show();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            initialize();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        if (isValidate()){
            clearFields();
        }}

    private void clearFields() {
        txtTableId.setText("");
        txtDescription.setText("");
        txtTables.setText("");
        txtseats.setText("");

    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        if (isValidate()) {
            String tableId = txtTableId.getText();
            String description = txtDescription.getText();
            int noOftables = Integer.parseInt(txtTables.getText());
            int noOfSeats = Integer.parseInt(txtseats.getText());

            try {
                boolean isSaved = tablesBO.saveTables( new TablesDTO(tableId, description, noOftables, noOfSeats));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Table saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
            initialize();
        }
    }
    public void ClickOnAction(MouseEvent mouseEvent) {
        TablesTM selectedItem = tblTables.getSelectionModel().getSelectedItem();
        txtTableId.setText(selectedItem.getTableId());
        txtDescription.setText(selectedItem.getDescription());
        txtTables.setText(String.valueOf(selectedItem.getNoOfTables()));
        txtseats.setText(String.valueOf(selectedItem.getNoOfSeats()));

    }

    public void searchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String tableId  = txtTableId.getText();

        try {
            TablesDTO  tablesDTO = tablesBO.searchTables(tableId);
            if (tablesDTO != null) {
                txtTableId.setText(tablesDTO.getTableId());
                txtDescription.setText(tablesDTO.getDescription());
                txtTables.setText(String.valueOf(tablesDTO.getNoOfTables()));
                txtseats.setText(String.valueOf(tablesDTO.getNoOfSeats()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }



    public void txtDescOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.DESC,txtDescription);
    }

    public void txtQtyTablesOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY,txtTables);
    }

    public void txtQtySeatsOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY,txtseats);
    }
    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.DESC,txtDescription))return false;
        if(!Regex.setTextColor(TextField.QTY,txtTables))return false;
        if(!Regex.setTextColor(TextField.QTY,txtseats))return false;
        return true;
    }
    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtTableId.setText(new TablesDAOImpl().autoGenarateId());
    }
}
