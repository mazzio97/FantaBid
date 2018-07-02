package org.fantabid.controller;

import org.fantabid.generated.tables.records.AllenatoreRecord;
import org.fantabid.model.Limits;
import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Dialogs;
import org.fantabid.view.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField surnameField;
    @FXML private TextField nameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Button backButton;
    @FXML private Button signupButton;
    
    public final void initialize() {
        nameField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_NAME_CHARS));
        surnameField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_SURNAME_CHARS));
        usernameField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_USERNAME_CHARS));
        passwordField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_PASSWORD_CHARS));
        confirmField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_PASSWORD_CHARS));
        signupButton.disableProperty().bind(new BooleanBinding() {{
                super.bind(nameField.textProperty(), surnameField.textProperty(), usernameField.textProperty(),
                           passwordField.textProperty(), confirmField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return nameField.getText().isEmpty() ||
                       surnameField.getText().isEmpty() ||
                       usernameField.getText().isEmpty() ||
                       passwordField.getText().isEmpty() ||
                       confirmField.getText().isEmpty();
            }
        });

        backButton.setOnAction(e -> Views.loadLoginScene());
        
        signupButton.setOnAction(e -> {            
            if(!passwordField.getText().equals(confirmField.getText())) {
                Dialogs.showErrorDialog("Password Error", "The given passwords are not the same");
            }
            else if (Queries.checkUsername(usernameField.getText())) {
                Dialogs.showWarningDialog("User Already Exists", "This username cannot be used, try another one");
            } else {
                AllenatoreRecord user = Queries.registerUser(nameField.getText(), surnameField.getText(), 
                                                             usernameField.getText(), passwordField.getText());
                Model.get().setUser(user);
                Views.loadUserAreaScene();
            }
        });
    }
    
}