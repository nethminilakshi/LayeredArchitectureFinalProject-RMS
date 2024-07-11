package lk.ijse.restaurantManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.bo.BOFactory;
import lk.ijse.restaurantManagement.bo.custom.ReservationBO;
import lk.ijse.restaurantManagement.bo.custom.ReservationDetailsBO;
import lk.ijse.restaurantManagement.dto.ReservationDTO;
import lk.ijse.restaurantManagement.dto.ReservationDetailDTO;
import lk.ijse.restaurantManagement.view.tdm.ReservationDetailTM;
import lk.ijse.restaurantManagement.view.tdm.ReservationTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetailsFormControlller {

        @FXML
        private TableColumn<?, ?> colCustomerId;

        @FXML
        private TableColumn<?, ?> colDate;

        @FXML
        private TableColumn<?, ?> colDescription;

        @FXML
        private TableColumn<?, ?> colReservationId;

        @FXML
        private TableColumn<?, ?> colStatus;

        @FXML
        private TableColumn<?, ?> colTime;

        @FXML
        private TableView<ReservationTM> tblReservation;
        @FXML
        private TableColumn<?, ?> colreserveId;

        @FXML
        private TableView<ReservationDetailTM> tblReserveDetails;

        @FXML
        private TableColumn<?, ?> colTableId;

        @FXML
        private TableColumn<?, ?> colReqQty;

        @FXML
        private AnchorPane root;
        private List<ReservationDTO> ReservationList=new ArrayList<>();
        private List<ReservationDetailDTO> ReservationdetailsList=new ArrayList<>();

        ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.Reservation);
        ReservationDetailsBO rdBO = (ReservationDetailsBO) BOFactory.getBoFactory().getBO(BOFactory.DAOType.ReserveDetails);

        public void initialize() throws ClassNotFoundException {

            this.ReservationList=getAllReservations();
            setCellValueFactory();
            loadItemTable();

            this.ReservationdetailsList=getallReservationdetails();
            setCellValueFactorynew();
            loadItemTablenew();

        }

        private List<ReservationDetailDTO> getallReservationdetails() throws ClassNotFoundException {

            List<ReservationDetailDTO> reservationDetailsList = null;
            try {
                reservationDetailsList=  rdBO.getAllReservations();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return reservationDetailsList;
        }

        private void loadItemTablenew() {
            ObservableList<ReservationDetailTM> tmList = FXCollections.observableArrayList();

            for (ReservationDetailDTO reservationDetails : ReservationdetailsList) {
                ReservationDetailTM reservationDetailsTm = new ReservationDetailTM(
                        reservationDetails.getReservationId(),
                        reservationDetails.getTableId(),
                        reservationDetails.getReqTablesQty()
                );


                tmList.add(reservationDetailsTm);
            }
            tblReserveDetails.setItems(tmList);
            ReservationDetailTM selectedItem = tblReserveDetails.getSelectionModel().getSelectedItem();
            System.out.println("selectedItem = " + selectedItem);
        }

        private void setCellValueFactorynew() {
            colreserveId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
            colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
            colReqQty.setCellValueFactory(new PropertyValueFactory<>("reqTablesQty"));

        }

        private void loadItemTable() {

            ObservableList<ReservationTM> tmList = FXCollections.observableArrayList();

            for (ReservationDTO reservation : ReservationList) {
                ReservationTM reservationTm = new ReservationTM(
                        reservation.getReservationId(),
                        reservation.getDescription(),
                        reservation.getCusId(),
                        reservation.getDate(),
                        reservation.getTime(),
                        reservation.getStatus()
                );


                tmList.add(reservationTm);
            }
            tblReservation.setItems(tmList);
            ReservationTM selectedItem = tblReservation.getSelectionModel().getSelectedItem();
            System.out.println("selectedItem = " + selectedItem);
        }

        private void setCellValueFactory() {
            colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        }

        private List<ReservationDTO> getAllReservations() throws ClassNotFoundException {
            List<ReservationDTO> reservationList = null;
            try {
                reservationList= reservationBO.getAllReservations();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return reservationList;
        }

        @FXML
        public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/reservation_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Dashboard Form");
            stage.centerOnScreen();
        }

    }


