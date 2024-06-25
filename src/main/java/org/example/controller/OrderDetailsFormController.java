package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import org.example.bo.BoFactory;
import org.example.bo.asset.PlaceOrderBo;
import org.example.entitiy.OrderEntity;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableView<OrderEntity> tblOrder;

    @FXML
    private AnchorPane viewOrdersWindow;

    private final SceneSwitchController sceneSwitch;

    private final PlaceOrderBo placeOrderBo;

    public OrderDetailsFormController() {
        this.sceneSwitch = SceneSwitchController.getInstance();
        this.placeOrderBo = BoFactory.getInstance().getBo(BoType.PLACEORDER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tblOrder.setItems(placeOrderBo.getAllOrders());
    }

    @FXML
    void btnCustomerDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"customer-details-form.fxml");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"manage-employee-form.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"order-details-form.fxml");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"place-order-form.fxml");
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"product-details-form.fxml");
    }

    @FXML
    void btnSupplierDetailsOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"supplier-details-form.fxml");
    }

    @FXML
    void lblClothifyMouseClicked(MouseEvent event) throws IOException {
        sceneSwitch.switchScene(viewOrdersWindow,"dashboard-form.fxml");
    }
}