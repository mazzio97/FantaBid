package org.fantabid.controller;

import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Dialogs;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signupButton;
    @FXML private Button loginButton;

    public final void initialize() {
        signupButton.setOnAction(e -> Views.loadSignupScene());
        
        loginButton.setOnAction(e -> {
            if (Queries.checkPassword(usernameField.getText(), passwordField.getText())) {
                Model.get().setUser(usernameField.getText());
                Views.loadUserAreaScene();
            } else {
                Dialogs.showErrorDialog("Wrong User or Password", "Reinsert the username or the password");
            }
        });
    }
}