package org.fantabid.controller;

import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.model.utils.Limits;
import org.fantabid.view.Dialogs;
import org.fantabid.view.Views;

import javafx.beans.binding.BooleanBinding;
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
        usernameField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_USERNAME_CHARS));
        passwordField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_PASSWORD_CHARS));
        
        loginButton.disableProperty().bind(new BooleanBinding() {{
                super.bind(usernameField.textProperty(), passwordField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return usernameField.getText().isEmpty() || passwordField.getText().isEmpty();
            }
        });

        signupButton.setOnAction(e -> Views.loadSignupScene());
        loginButton.setOnAction(e ->  Queries.getUser(usernameField.getText())
                                             .filter(u -> u.getPassword().equals(passwordField.getText()))
                                             .map(u -> {
                                                 Model.get().setUser(u);
                                                 Views.loadUserAreaScene();
                                                 return u;
                                             })
                                             .orElseGet(() -> {
                                                 Dialogs.showErrorDialog("Wrong User or Password",
                                                                         "Reinsert the username or the password");
                                                 return null;
                                             }));
    }
}