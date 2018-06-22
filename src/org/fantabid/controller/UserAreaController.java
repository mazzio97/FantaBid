package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserAreaController {
    
    @FXML private Label usernameLabel;
    @FXML private Button leaguePlaceholder;
    @FXML private Button findLeagueButton;
    
    public final void initialize() {
        usernameLabel.setText("Player Name");
        leaguePlaceholder.setOnAction(e -> Views.loadLeagueScene());
        findLeagueButton.setOnAction(e -> Views.loadLeaguesScene());
    }
}
