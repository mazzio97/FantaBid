package org.fantabid.controller;

import org.fantabid.utils.Model;
import org.fantabid.utils.Queries;
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
            if (Queries.checkUsername(usernameField.getText())) {
                Dialogs.showWarningDialog("User already existing", "The username inserted cannot be used");
            } else {
                Queries.registerUser(nameField.getText(), surnameField.getText(), 
                                     usernameField.getText(), passwordField.getText());
                Model.get().setUser(usernameField.getText());
                Views.loadUserAreaScene();
            }
        });
    }
    
}