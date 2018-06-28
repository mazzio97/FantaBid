package org.fantabid.controller;

import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Dialogs;
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
        
        signupButton.setOnAction(e -> {            
            if(!passwordField.getText().equals(confirmField.getText())) {
                Dialogs.showErrorDialog("Password Error", "The given passwords are not the same");
            }
            else if (Queries.checkUsername(usernameField.getText())) {
                Dialogs.showWarningDialog("User Already Exists", "This username cannot be used, try another one");
            } else {
                Queries.registerUser(nameField.getText(), surnameField.getText(), 
                                     usernameField.getText(), passwordField.getText());
                Model.get().setUser(usernameField.getText());
                Views.loadUserAreaScene();
            }
        });
    }
    
}