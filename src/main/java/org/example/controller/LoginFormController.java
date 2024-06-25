package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BoFactory;
import org.example.bo.asset.UserBo;
import org.example.util.BoType;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private CheckBox cbShowPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtShowPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane loginWindow;

    private final UserBo userBo;

    private final SceneSwitchController sceneSwitch;


    public LoginFormController() {
        this.userBo = BoFactory.getInstance().getBo(BoType.USER);
        this.sceneSwitch = SceneSwitchController.getInstance();
    }

    @FXML
    void ForgotPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void SignUpOnAction(ActionEvent event) throws IOException {
        sceneSwitch.switchScene(loginWindow,"sign-up-form.fxml");
    }

    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        if (txtUserName.getText().isEmpty() && txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Text fields can't be empty!").show();
        } else {
            if (userBo.checkIfUserPasswordMatches(txtUserName.getText(), txtPassword.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Login Success").show();
                sceneSwitch.switchScene(loginWindow,"dashboard-form.fxml");
            } else {
                new Alert(Alert.AlertType.ERROR, "Username, Password didn't match. Try again...").show();
            }

        }
    }

    @FXML
    void cbShowPasswordOnAction(ActionEvent event) {
        if (cbShowPassword.isSelected()){
            txtShowPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            txtShowPassword.setVisible(true);
        } else {
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
        }
    }

}