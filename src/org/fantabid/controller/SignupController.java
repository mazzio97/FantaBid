package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField surnameField;
    @FXML private TextField nameField;
    @FXML private PasswordField confirmField;
    @FXML private PasswordField passwordField;
    @FXML private Button backButton;
    @FXML private Button signupButton;
    
    public final void initialize() {
        backButton.setOnAction(e -> Views.loadLoginScene());
        signupButton.setOnAction(e -> Views.loadUserAreaScene());
    }
    
}