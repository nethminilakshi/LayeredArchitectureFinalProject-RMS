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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.bo.BOFactory;
import lk.ijse.restaurantManagement.bo.custom.CustomerBO;
import lk.ijse.restaurantManagement.bo.custom.ReservationBO;
import lk.ijse.restaurantManagement.bo.custom.TablesBO;
import lk.ijse.restaurantManagement.dao.DAOFactory;
import lk.ijse.restaurantManagement.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.restaurantManagement.dto.CustomerDTO;
import lk.ijse.restaurantManagement.dto.ReservationDTO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.dto.TablesDTO;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import lk.ijse.restaurantManagement.view.tdm.ResevationCartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    public class ReservationFormController {


        @FXML
        private ComboBox<String> cmbStatus;

        @FXML
        private ComboBox<String> cmbTableId;

        @FXML
        private ComboBox<String> cmbTimeSlot;

        @FXML
        private TableColumn<?, ?> colAction;

        @FXML
        private TableColumn<?, ?> colDate;

        @FXML
        private TableColumn<?, ?> colRequiredTableQty;

        @FXML
        private TableColumn<?, ?> colReservationId;

        @FXML
        private TableColumn<?, ?> colTableId;

        @FXML
        private TableColumn<?, ?> colTime;

        @FXML
        private AnchorPane root;

        @FXML
        private TableView<ResevationCartTM> tblReservationCart;

        @FXML
        private JFXTextField txtAvailableQty;

        @FXML
        private JFXTextField txtContact;

        @FXML
        private JFXTextField txtCustomerId;

        @FXML
        private DatePicker txtDate;

        @FXML
        private JFXTextField txtDescription;

        @FXML
        private JFXTextField txtRequiredQty;

        @FXML
        private JFXTextField txtReservationId;

        private final ObservableList<ResevationCartTM> cartList = FXCollections.observableArrayList();
        CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Customer);
        ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Reservation);
        TablesBO tablesBO= (TablesBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Tables);

        public void initialize(){

            try {
                autoGenarateId();
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            setCellValueFactory();
            loadTable();
            getStatus();
            getTimeSlot();
            getTableIds();

        }

        private void setCellValueFactory() {
            colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
            colRequiredTableQty.setCellValueFactory(new PropertyValueFactory<>("tablesQty"));
            colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
        }

        private void loadTable() {
            ObservableList<ResevationCartTM> tmList = FXCollections.observableArrayList();

            for (ResevationCartTM cart : cartList) {
                ResevationCartTM cartTm = new ResevationCartTM(
                        cart.getReservationId(),
                        cart.getDate(),
                        cart.getTime(),
                        cart.getTableId(),
                        cart.getTablesQty(),
                        cart.getBtnRemove()
                );


                tmList.add(cartTm);
            }
            tblReservationCart.setItems(tmList);
            ResevationCartTM selectedItem = tblReservationCart.getSelectionModel().getSelectedItem();
            System.out.println("selectedItem = " + selectedItem);
        }

        private String[] Status={"available","Reserved"};

        public void getStatus(){
            List<String> statusList = new ArrayList<>();
            for(String data: Status){
                statusList.add(data);
            }
            ObservableList<String> obList= FXCollections.observableArrayList(statusList);
            cmbStatus.setItems(obList);
        }

        private String[] timeSlot={"morning","afternoon","evening","night"};

        public void getTimeSlot(){
            List<String> timeList = new ArrayList<>();
            for(String data: timeSlot){
                timeList.add(data);
            }
            ObservableList<String> obList= FXCollections.observableArrayList(timeList);
            cmbTimeSlot.setItems(obList);
        }

        private void getTableIds() {
            ObservableList<String> obList = FXCollections.observableArrayList();
            try {

                List<String> idList = tablesBO.getTablesIds();

                for (String code : idList) {
                    obList.add(code);
                }

                cmbTableId.setItems(obList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        public void btnAddToCartOnAction(ActionEvent actionEvent) {
            if (isValidate()){
                String reservationId = txtReservationId.getText();
                String date = String.valueOf(txtDate.getValue());
                String time = String.valueOf(cmbTimeSlot.getValue());
                String tableId = String.valueOf(cmbTableId.getValue());
                int tablesQty = Integer.parseInt(txtRequiredQty.getText());
                JFXButton btnRemove = new JFXButton("cancel");
                btnRemove.setCursor(Cursor.HAND);

                btnRemove.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to cancel?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblReservationCart.getSelectionModel().getSelectedIndex();
                        cartList.remove(selectedIndex);

                        tblReservationCart.refresh();

                    }
                });

                for (int i = 0; i < tblReservationCart.getItems().size(); i++) {
                    if (tableId.equals(colTableId.getCellData(i))) {
                        tablesQty += cartList.get(i).getTablesQty();

                        cartList.get(i).setTablesQty(tablesQty);

                        tblReservationCart.refresh();

                        txtRequiredQty.setText("");
                        return;
                    }
                }

                ResevationCartTM cartTm = new ResevationCartTM(reservationId, date, time, tableId, tablesQty, btnRemove);

                cartList.add(cartTm);

                tblReservationCart.setItems(cartList);
                txtRequiredQty.setText("");

            }}

       @FXML
       public void btnAddOnAction(ActionEvent actionEvent) {
            if (isValidate()){
                String reservationId = txtReservationId.getText();
                String description= txtDescription.getText();
                String cusId = txtCustomerId.getText();
                String date = String.valueOf(txtDate.getValue());
                String time = cmbTimeSlot.getValue();
                String status = cmbStatus.getValue();


                var reservation = new ReservationDTO(reservationId,description, cusId, date,time,status);

                List<ReservationDetailDTO> reservationDetailsList = new ArrayList<>();
                for (int i = 0; i < tblReservationCart.getItems().size(); i++) {
                    ResevationCartTM tm = cartList.get(i);

                    ReservationDetailDTO od = new ReservationDetailDTO(
                            reservationId,
                            tm.getTableId(),
                            tm.getTablesQty()

                    );
                    reservationDetailsList.add(od);
                }


                try {
                    boolean isPlaced = saveReservation(reservationId,description,cusId,date,time,status, reservationDetailsList);
                    if(isPlaced) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Reservation Ok!").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Reservation not placed!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
                clearFields();
            }
        }

        public boolean saveReservation(String reservationId,String description,String cusId,String date,String time,String status,List<ReservationDetailDTO> rsDetails) throws SQLException {
            return reservationBO.PlaceReservation(reservationId,description,cusId,date,time,status,rsDetails);
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
        public void btnClearOnAction(ActionEvent actionEvent) {
            if (isValidate()){
                clearFields();
            }}

        private void clearFields() {
            txtDescription.setText("");
            txtDate.setValue(LocalDate.parse(""));
            txtAvailableQty.setText("");
            cmbTableId.setValue("");
            txtRequiredQty.setText("");
            cmbStatus.setValue("");
            cmbTimeSlot.setValue("");
            txtCustomerId.setText("");
            txtContact.setText("");
        }


        @FXML
        private void autoGenarateId() throws SQLException, ClassNotFoundException {
            txtReservationId.setText(new ReservationDAOImpl().autoGenarateId());
        }


        public void txtQtyOnAction(ActionEvent actionEvent) {
            btnAddToCartOnAction(actionEvent);
        }


       public void searchContactOnAction(ActionEvent actionEvent) {
            String contact  = txtContact.getText();

            try {
                CustomerDTO customerDTO = customerBO.searchCustomer(contact);

                if (customerDTO != null) {
                    txtCustomerId.setText(customerDTO.getCusId());
                    txtContact.setText(customerDTO.getContact());
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Not Found Customer").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            initialize();
        }

        public void searchOnIdAction(ActionEvent actionEvent) {
            String cusId  = txtCustomerId.getText();

            try {
                ReservationDTO reservationDTO = reservationBO.searchReservation(cusId);

                if (reservationDTO != null) {
                    txtReservationId.setText(reservationDTO.getReservationId());
                    txtDescription.setText(reservationDTO.getDescription());
                    txtCustomerId.setText(reservationDTO.getCusId());
                    txtDate.setValue(LocalDate.parse(reservationDTO.getDate()));
                    cmbTimeSlot.setValue(reservationDTO.getTime());



                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            initialize();
        }

        public void cmbTablesOnAction(ActionEvent actionEvent) {
            String tableId = cmbTableId.getValue();
            try {
                TablesDTO tables = tablesBO.searchTables(tableId);
                if (tables != null) {
                    txtAvailableQty.setText(String.valueOf(tables.getNoOfTables()));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            txtRequiredQty.requestFocus();
        }

       /* public void searchOnAction(ActionEvent actionEvent) {
            String date  = String.valueOf(txtDate.getValue());

            try {
                ReservationNew reseve = newRepo.searchByDate(date);

                if (reseve  != null) {
                    txtReservationId.setText(reseve.getReservationId());
                    cmbTableId.setValue(reseve.getTableId());
                    txtAvailableQty.setText(String.valueOf(reseve.getNoOfTables()));
                    txtDate.setValue(LocalDate.parse(reseve.getDate()));

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            initialize();
        }

      */
        public void btnNextOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/mailSender_form.fxml"));
            Stage stage = new Stage();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Mail Sender Form");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            stage.show();
        }
        public void btnReservationDetails(ActionEvent actionEvent) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/resevationDetails_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("ReservationDetails Form");
            stage.centerOnScreen();
        }

        public void txtDescOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.DESC,txtDescription);
        }

        public void txtAContactOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.CONTACT,txtContact);
        }

        public void txtQtyOnKeyReleased(KeyEvent keyEvent) {
            Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.QTY,txtRequiredQty);
        }
        public boolean isValidate(){
            if(!Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.DESC,txtDescription))return false;
            if(!Regex.setTextColor(lk.ijse.restaurantManagement.util.TextField.CONTACT,txtContact))return false;
            if(!Regex.setTextColor(TextField.QTY,txtRequiredQty))return false;
            return true;
        }

    }




