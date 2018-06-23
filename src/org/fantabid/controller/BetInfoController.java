package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class BetInfoController {

    @FXML private Label playerLabel;
    @FXML private Button goalkeeperButton;
    @FXML private Button defenderButton;
    @FXML private Button midfielderButton;
    @FXML private Button strikerButton;
    @FXML private Label lastBetLabel;
    @FXML private Label timeLeftLabel;
    @FXML private Slider betSlider;
    @FXML private Label betLabel;
    @FXML private Button betButton;
    
    public final void initialize() {
        playerLabel.setText("Player Name");
        goalkeeperButton.setDisable(false);
        lastBetLabel.setText("Last Bet: ");
        timeLeftLabel.setText("Time Left: ");
        betLabel.textProperty().bind(Bindings.format("%.0f $", betSlider.valueProperty()));
        betButton.setOnAction(e -> Views.loadLeagueScene());
    }
}
