package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LeaguesController {

    @FXML private Button leaguePlaceholder;
    @FXML private Button createLeagueButton;
    @FXML private Button backButton;

    public final void initialize() {
        leaguePlaceholder.setOnAction(e -> Views.loadLeagueSignupScene());
        createLeagueButton.setOnAction(e -> Views.loadNewLeagueScene());
        backButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}
