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
import lk.ijse.restaurantManagement.bo.custom.ItemBO;
import lk.ijse.restaurantManagement.dao.custom.impl.ItemDAOImpl;
import lk.ijse.restaurantManagement.dto.ItemDTO;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {
    @FXML
    private ComboBox<String> cmbStatus;


    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemTM> tblItem;
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;
    private List<ItemDTO> itemList=new ArrayList<>();
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Item);


    public void initialize() throws ClassNotFoundException {

        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        this.itemList=getAllItems();
        getItemStatus();
        setCellValueFactory();
        loadItemTable();

    }

    private List<ItemDTO> getAllItems() throws ClassNotFoundException {
        ArrayList<ItemDTO> itemList = null;
        try {
            itemList = itemBO.getAllItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }


    private String[] statusList={"Available","Unavailable"};

    public void getItemStatus(){
        List<String> statuslist = new ArrayList<>();
        for(String data: statusList){
            statuslist.add(data);
        }
        ObservableList<String> obList= FXCollections.observableArrayList(statuslist);
        cmbStatus.setItems(obList);
    }
    private void loadItemTable() {
        ObservableList<ItemTM> tmList = FXCollections.observableArrayList();

        for (ItemDTO itemDTO : itemList) {
            ItemTM itemTM = new ItemTM(
                    itemDTO.getId(),
                    itemDTO.getDescription(),
                    itemDTO.getQtyOnHand(),
                    itemDTO.getUnitPrice(),
                    itemDTO.getStatus()
            );
            tmList.add(itemTM);
        }
        tblItem.setItems(tmList);
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        if (isValidate()) {
            String description = txtName.getText();

            try {
                boolean isDeleted = itemBO.deleteItem(description);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item deleted!").show();
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
            String id = txtId.getText();
            String name = txtName.getText();
            String qtyOnHand = txtQtyOnHand.getText();
            String unitPrice = txtUnitPrice.getText();
            String status=String.valueOf(cmbStatus.getValue());

            try {
                boolean isSaved = itemBO.saveItem(new ItemDTO(id, name, qtyOnHand,unitPrice,status));
                if(isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
            initialize();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtQtyOnHand.setText("");
        txtUnitPrice.setText("");
        cmbStatus.setValue("");
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        if (isValidate()){
            clearFields();
        }}

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        if (isValidate()) {
            String id = txtId.getText();
            String name = txtName.getText();
            String qtyOnHand = txtQtyOnHand.getText();
            String unitPrice = txtUnitPrice.getText();
            String status = String.valueOf(cmbStatus.getValue());


            try {
                boolean isUpdated = itemBO.updateItem(new ItemDTO(id, name, qtyOnHand, unitPrice, status));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
            initialize();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent)throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {

        String id  = txtName.getText();

        try {
            ItemDTO itemDTO = itemBO.searchItem(id);

            if (itemDTO != null) {
                txtId.setText(itemDTO.getId());
                txtName.setText(itemDTO.getDescription());
                txtQtyOnHand.setText(itemDTO.getQtyOnHand());
                txtUnitPrice.setText(itemDTO.getUnitPrice());
                cmbStatus.setValue(itemDTO.getStatus());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }



    public void tblClickOnAction(MouseEvent mouseEvent) {
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
        txtId.setText(selectedItem.getId());
        txtName.setText(selectedItem.getDescription());
        txtQtyOnHand.setText(selectedItem.getQtyOnHand());
        txtUnitPrice.setText(selectedItem.getUnitPrice());
        cmbStatus.setValue(selectedItem.getStatus());
    }
    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtId.setText(new ItemDAOImpl().autoGenarateId());
    }

    public void txtItemUnitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.UNITPRICE,txtUnitPrice);
    }

    public void txtitemQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY,txtQtyOnHand);
    }

    public void txtItemNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtName);
    }
    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.UNITPRICE,txtUnitPrice))return false;
        if(!Regex.setTextColor(TextField.QTY,txtQtyOnHand))return false;
        if(!Regex.setTextColor(TextField.NAME,txtName))return false;
        return true;
    }
}
