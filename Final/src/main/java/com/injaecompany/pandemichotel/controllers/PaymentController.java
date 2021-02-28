package com.injaecompany.pandemichotel.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.injaecompany.pandemichotel.config.StageManager;
import com.injaecompany.pandemichotel.models.Payment;
import com.injaecompany.pandemichotel.services.impl.PaymentService;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


@Controller
public class PaymentController implements Initializable {

    @FXML
    private Label paymentId;

    @FXML
    private TextField amount;
    
    @FXML
    private Button reset;

    @FXML
    private Button confirm;
    
    @FXML
    private ToggleGroup paymentMethod;
    
    @FXML
    private RadioButton rbCash;

    @FXML
    private RadioButton rbCard;

    @FXML
    private TableView<Payment> paymentTable;

    @FXML
    private TableColumn<Payment, Long> colPaymentId;

    @FXML
    private TableColumn<Payment, String> colAmount;
    
    @FXML
    private TableColumn<Payment, String> colPaymentMethod;

    @FXML
    private TableColumn<Payment, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private PaymentService paymentService;

    private ObservableList<Payment> paymentList = FXCollections.observableArrayList();

////    @FXML
////    private void exit(ActionEvent event) {
////        Platform.exit();
////    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void savePayment(ActionEvent event){
        if (validate("Amount", getAmount(), "([0-9]{4,8}\\s*)+")) {

            if (paymentId.getText() == null || "".equals(paymentId.getText())) {
                if (true) {

                    Payment payment = new Payment();
                    payment.setAmount(getAmount());
                    payment.setPaymentMethod(getPaymentMethod());

                    Payment newPayment =  paymentService.save(payment);

                    saveAlert(newPayment);
                }

            } else {
                Payment payment = paymentService.find(Long.parseLong(paymentId.getText()));
                payment.setAmount(getAmount());
                payment.setPaymentMethod(getPaymentMethod());
                Payment updatedPayment = paymentService.update(payment);
                updateAlert(updatedPayment);
            }

            clearFields();
            loadUserDetails();
        }
    }

    @FXML
    private void deletePayment(ActionEvent event) {
        List<Payment> payment = paymentTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            paymentService.deleteInBatch(payment);
        }

        loadUserDetails();
    }

    private void clearFields() {
        paymentId.setText(null);
        amount.clear();
        rbCash.setSelected(true);
        rbCard.setSelected(false);

    }

    private void saveAlert(Payment payment) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Customer saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The payment " + payment.getAmount() +  " has been created.");
        alert.showAndWait();
    }

    private void updateAlert(Payment payment) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Payment updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The payment " + payment.getAmount() + " has been updated.");
        alert.showAndWait();
    }

    public String getAmount() {
        return amount.getText();
    }
    
    public String getPaymentMethod() {
        return rbCash.isSelected() ? "Cash" : "Card";
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

        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Payment, Boolean>, TableCell<Payment, Boolean>> cellFactory
            = new Callback<TableColumn<Payment, Boolean>, TableCell<Payment, Boolean>>() {
        @Override
        public TableCell<Payment, Boolean> call(final TableColumn<Payment, Boolean> param) {
            final TableCell<Payment, Boolean> cell = new TableCell<Payment, Boolean>() {
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
                            Payment payment = getTableView().getItems().get(getIndex());
                            updatedPayment(payment);
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

                private void updatedPayment(Payment payment) {
                    paymentId.setText(Long.toString(payment.getId()));
                    amount.setText(payment.getAmount());
                    if (payment.getPaymentMethod().equals("Cash")) {
                        rbCash.setSelected(true);
                    } else {
                        rbCard.setSelected(true);
                    }
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        paymentList.clear();
        paymentList.addAll(paymentService.findAll());

        paymentTable.setItems(paymentList);
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

        paymentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadUserDetails();
    }
}