package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LeagueSignupController {
    
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label infoLabel;
    @FXML private Label rulesLabel;
    @FXML private Button registerButton;
    @FXML private Button cancelButton;

    public final void initialize() {
        nameLabel.setText("League Name");
        descriptionLabel.setText("League Description");
        infoLabel.setText("League Info");
        rulesLabel.setText("League Rules");
        registerButton.setOnAction(e -> Views.loadUserAreaScene());
        cancelButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}
