package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private TextField surnameField;
    @FXML private TextField nameField;
    @FXML private PasswordField confirmField;
    @FXML private PasswordField passwordField;
    @FXML private Button signupButton;
    @FXML private Button loginButton;

    public final void initialize() {
        signupButton.setOnAction(e -> Views.loadSignupScene());
        loginButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}