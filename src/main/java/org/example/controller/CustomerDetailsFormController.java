package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BoFactory;
import org.example.bo.asset.CustomerBo;
import org.example.bo.asset.impl.CustomerBoImpl;
import org.example.model.Customer;
import org.example.model.User;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerDetailsFormController implements Initializable {

    @FXML
    private Button btnAction;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private TableColumn<?, ?> colCustomerEmail;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerEmail;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private AnchorPane customerWindow;

    private String currentId;

    private final CustomerBo customerBo;

    private final SceneSwitchController sceneSwitch;

    public CustomerDetailsFormController() {
        this.customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
        this.sceneSwitch = SceneSwitchController.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCustomerId.setEditable(false);
        loadCustomerId();

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadCustomerTbl();
    }

    @FXML
    void btnActionOnAction(ActionEvent event) {
        txtCustomerId.setEditable(true);
        txtCustomerId.setText("");
        btnAdd.setVisible(false);
        btnClear.setVisible(false);
        btnAction.setVisible(false);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (!areTextFieldsEmpty()) {
            String email = txtCustomerEmail.getText();
            if (customerBo.isValidEmail(email)){
                Customer customer = new Customer(
                        currentId,
                        txtCustomerName.getText(),
                        email,
                        txtCustomerAddress.getText()
                );

                customerBo.insertCustomer(customer);

                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully").show();

                clearTextFields();
                loadCustomerId();
                loadCustomerTbl();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Email. Try again...").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!areTextFieldsEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            // Check if the response was OK or Cancel
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (customerBo.deleteCustomerById(txtCustomerId.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed. Try again...").show();
                }
            }
            loadCustomerTbl();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    private void clearTextFields(){
        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerAddress.setText("");
    }

    private void loadCustomerId(){
        currentId = customerBo.generateCustomerId();
        txtCustomerId.setText(currentId);
    }

    private void loadCustomerTbl(){
        tblCustomer.setItems(customerBo.getAllCustomers());
    }

    @FXML
    void btnManageCustomersOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"customer-details-form.fxml");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"manage-employee-form.fxml");
    }

    @FXML
    void btnManageOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"order-details-form.fxml");
    }

    @FXML
    void btnManageProductsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"product-details-form.fxml");
    }

    @FXML
    void btnManageSuppliersOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"supplier-details-form.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"place-order-form.fxml");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!areTextFieldsEmpty()) {
            Customer customer = customerBo.getCustomerById(txtCustomerId.getText());

            if (customer != null){
                customer.setName(txtCustomerName.getText());
                customer.setAddress(txtCustomerAddress.getText());
                customer.setEmail(txtCustomerEmail.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
                Optional<ButtonType> result = alert.showAndWait();
                // Check if the response was OK or Cancel
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (customerBo.updateCustomer(customer)) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully").show();
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Update failed. Try again...").show();
                    }
                }
            }else {
                new Alert(Alert.AlertType.WARNING, "Customer does not exists...").show();
            }
            loadCustomerTbl();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    @FXML
    void lblClothifyMouseClicked(MouseEvent event) throws IOException {
        sceneSwitch.switchScene(customerWindow,"dashboard-form.fxml");
    }

    private boolean areTextFieldsEmpty() {
        return txtCustomerId.getText().isEmpty() &&
                txtCustomerName.getText().isEmpty() &&
                txtCustomerEmail.getText().isEmpty() &&
                txtCustomerAddress.getText().isEmpty();
    }
}