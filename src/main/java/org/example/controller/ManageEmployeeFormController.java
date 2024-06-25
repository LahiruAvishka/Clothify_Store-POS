package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import org.example.bo.BoFactory;
import org.example.bo.asset.UserBo;
import org.example.bo.asset.impl.UserBoImpl;
import org.example.model.User;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class ManageEmployeeFormController implements Initializable {

    @FXML
    private TableView<User> tblEmployee;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TableColumn<User, String> colEmployeeAddress;
    // TableColumn<TableView Generic Type, content Type in cells>

    @FXML
    private TableColumn<User, String> colEmployeeEmail;

    @FXML
    private TableColumn<User, String> colEmployeeId;

    @FXML
    private TableColumn<User, String> colEmployeeName;

    @FXML
    private AnchorPane manageEmployeeWindow;

    private final UserBo userBo;

    private final SceneSwitchController sceneSwitch;

    public ManageEmployeeFormController() {
        this.sceneSwitch = SceneSwitchController.getInstance();
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmployeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadEmployeeTbl();
    }


    private void clearTextFields(){
        txtEmployeeName.setText("");
        txtEmployeeEmail.setText("");
        txtEmployeeAddress.setText("");
    }


    private void loadEmployeeTbl(){
        tblEmployee.setItems(userBo.getAllUsers());
    }

    @FXML
    void lblClothifyClickEvent(MouseEvent event) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"dashboard-form.fxml");
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"place-order-form.fxml");
    }

    public void btnManageEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"manage-employee-form.fxml");
    }

    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"order-details-form.fxml");
    }

    public void btnProductDetailsOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"product-details-form.fxml");
    }

    public void btnCustomerDetailsOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"customer-details-form.fxml");
    }

    public void btnSupplierDetailsOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(manageEmployeeWindow,"supplier-details-form.fxml");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!areTextFieldsEmpty()) {
            User user = userBo.getUserById(txtEmployeeId.getText());

            if (user != null){
                user.setName(txtEmployeeName.getText());
                user.setAddress(txtEmployeeAddress.getText());
                user.setEmail(txtEmployeeEmail.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
                Optional<ButtonType> result = alert.showAndWait();
                // Check if the response was OK or Cancel
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (userBo.updateUser(user)) {
                        new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully").show();
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Update failed. Try again...").show();
                    }
                }
            }else {
                new Alert(Alert.AlertType.WARNING, "User does not exists...").show();
            }
            loadEmployeeTbl();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!areTextFieldsEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            // Check if the response was OK or Cancel
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (userBo.deleteUserById(txtEmployeeId.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed. Try again...").show();
                }
            }
            loadEmployeeTbl();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input fields can't be empty!").show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

    private boolean areTextFieldsEmpty() {
        return txtEmployeeId.getText().isEmpty() &&
                txtEmployeeName.getText().isEmpty() &&
                txtEmployeeEmail.getText().isEmpty() &&
                txtEmployeeAddress.getText().isEmpty();
    }
}
