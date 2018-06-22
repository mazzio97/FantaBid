package org.fantabid.controller;


import org.fantabid.view.Dialogs;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class LeaguesController {

    @FXML private Button leaguePlaceholder;
    @FXML private Button createLeagueButton;

    public final void initialize() {
        leaguePlaceholder.setOnAction(e -> {
            Dialogs.showConfirmationDialog("Signing to League Name", "Are you sure you want to sign in?")
                   .filter(ButtonType.OK::equals)
                   .ifPresent(b -> Views.loadUserAreaScene());
        });
        createLeagueButton.setOnAction(e -> Views.loadNewLeagueScene());
    }
}
