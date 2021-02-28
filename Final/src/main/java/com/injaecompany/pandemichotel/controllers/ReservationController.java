package com.injaecompany.pandemichotel.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.injaecompany.pandemichotel.config.StageManager;
import com.injaecompany.pandemichotel.models.Reservation;
import com.injaecompany.pandemichotel.services.impl.ReservationService;
import com.injaecompany.pandemichotel.views.FxmlView;

//import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class ReservationController implements Initializable {

    @FXML
    private Label reservationId;

//    @FXML
//    private TextField firstName;
//
//    @FXML
//    private TextField lastName;
    
    @FXML
    private TextField contactNumber;
    
    @FXML
    private ComboBox roomNumberBox;
    
    @FXML
    private ComboBox childBox;
            
    @FXML
    private ComboBox adultBox;
    
    @FXML
    private DatePicker dob;

    @FXML
    private DatePicker dob1;

    @FXML
    private Button reset;
    
    @FXML
    private Button btnCustomer;

    @FXML
    private Button saveReservation;
    
    @FXML
    private Button deleteReservation;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Long> colReservationId;

    @FXML
    private TableColumn<Reservation, String> colDateCreated;

    @FXML
    private TableColumn<Reservation, String> colDateUpdated;
    
    @FXML
    private TableColumn<Reservation, String> colContactNumber;
    
    @FXML
    private TableColumn<Reservation, String> colChild;
    
    @FXML
    private TableColumn<Reservation, String> colAdult;
    
    @FXML
    private TableColumn<Reservation, String> colRoomNumber;

    @FXML
    private TableColumn<Reservation, LocalDate> colDOB;
    
    @FXML
    private TableColumn<Reservation, LocalDate> colDOB2;

    @FXML
    private TableColumn<Reservation, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ReservationService reservationService;

    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    
    private ObservableList<String> roomNumberList = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    
    private ObservableList<String> childList = FXCollections.observableArrayList("No Children", "1 Child", "2 Children", "3 Children", "4 Children", "5 Children");
    
    private ObservableList<String> adultList = FXCollections.observableArrayList("1 Adult", "2 Adults", "3 Adults", "4 Adults", "5 Adults");
    

////    @FXML
////    private void exit(ActionEvent event) {
////        Platform.exit();
////    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    void openCustomer(ActionEvent event) {
        stageManager.switchScene(FxmlView.CUSTOMER);
    }

    
    @FXML
    private void saveReservation(ActionEvent event) {

        if (validate("Contact Number", getContactNumber(), "([0-9]{11}\\s*)+")
                && emptyValidation("Room Number", roomNumberBox.getEditor().toString().isEmpty())
                && emptyValidation("Child", childBox.getEditor().toString().isEmpty())
                && emptyValidation("Adult", adultBox.getEditor().toString().isEmpty())
                && emptyValidation("DOB", dob.getEditor().getText().isEmpty())
                && emptyValidation1("DOB1", dob1.getEditor().getText().isEmpty())) {

            if (reservationId.getText() == null || "".equals(reservationId.getText())) {
                if (true) {

                    Reservation reservation = new Reservation();
                    reservation.setContactNumber(getContactNumber());
                    reservation.setChild(getChild());
                    reservation.setAdult(getAdult());
                    reservation.setRoomNumber(getRoomNumber());
                    reservation.setDob(getDob());
                    reservation.setDob1(getDob1());
                    reservation.setCreatedAt();
                    reservation.setUpdatedAt();

                    Reservation newReservation = reservationService.save(reservation);

                    saveAlert(newReservation);
                }

            } else {
                Reservation reservation = reservationService.find(Long.parseLong(reservationId.getText()));
                reservation.setContactNumber(getContactNumber());
                reservation.setChild(getChild());
                reservation.setAdult(getAdult());
                reservation.setRoomNumber(getRoomNumber());
                reservation.setDob(getDob());
                reservation.setDob1(getDob1());
                reservation.setCreatedAt();
                reservation.setUpdatedAt();
                Reservation updatedReservation = reservationService.update(reservation);
                updateAlert(updatedReservation);
            }

            clearFields();
            loadUserDetails();
            
            
        }
    
    }

    @FXML
    private void deleteReservation(ActionEvent event) {
        List<Reservation> users = reservationTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            reservationService.deleteInBatch(users);
        }

        loadUserDetails();
    }

    private void clearFields() {
        reservationId.setText(null);
        contactNumber.clear();
        childBox.getEditor().clear();
        adultBox.getEditor().clear();
        roomNumberBox.getEditor().clear();
        dob.getEditor().clear();
        dob1.getEditor().clear();
    }

    private void saveAlert(Reservation reservation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reservation saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Reservation for " + reservation.getContactNumber() + " has been created and Room Number is " + reservation.getRoomNumber() + " id is " + reservation.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Reservation reservation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reservation updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The reservation " + reservation.getContactNumber() +"and Room Number " + reservation.getRoomNumber() + " has been updated.");
        alert.showAndWait();
    }

    
    public String getContactNumber() {
        return contactNumber.getText();
    }
    
    public String getChild() {
        return childBox.getSelectionModel().getSelectedItem().toString();
    }
        
    public String getAdult() {
        return adultBox.getSelectionModel().getSelectedItem().toString();
    }
    
    public String getRoomNumber() {
        return roomNumberBox.getSelectionModel().getSelectedItem().toString();
    }
    
    public LocalDate getDob() {
        return dob.getValue();
    }
    
    public LocalDate getDob1() {
        return dob1.getValue();
    }

    

    /*
	 *  Set All userTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colReservationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colChild.setCellValueFactory(new PropertyValueFactory<>("child"));
        colAdult.setCellValueFactory(new PropertyValueFactory<>("adult"));
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colDOB2.setCellValueFactory(new PropertyValueFactory<>("dob1"));
        colDateCreated.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        //colDateUpdated.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>> cellFactory
            = new Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>>() {
        @Override
        public TableCell<Reservation, Boolean> call(final TableColumn<Reservation, Boolean> param) {
            final TableCell<Reservation, Boolean> cell = new TableCell<Reservation, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            updateReservation(reservation);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateReservation(Reservation reservation) {
                    reservationId.setText(Long.toString(reservation.getId()));
                    contactNumber.setText(reservation.getContactNumber());
                    adultBox.setValue(reservation.getAdult());
                    childBox.setValue(reservation.getChild());
                    roomNumberBox.setValue(reservation.getRoomNumber());
                    dob.setValue(reservation.getDob());
                    dob1.setValue(reservation.getDob1());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        reservationList.clear();
        reservationList.addAll(reservationService.findAll());
        reservationTable.setItems(reservationList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }
    
    private boolean emptyValidation1(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reservationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roomNumberBox.setItems(roomNumberList);
        childBox.setItems(childList);
        adultBox.setItems(adultList);

        setColumnProperties();

        // Add all users into table
        loadUserDetails();
    }
}