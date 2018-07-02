package org.fantabid.controller;

import static org.fantabid.model.Role.*;

import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.model.Model;
import org.fantabid.model.Queries;
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
    @FXML private Button cancelButton;
    @FXML private Button betButton;
    private final Model model = Model.get();
    
    public final void initialize() {
        CalciatoreRecord player = Queries.getPlayer(model.getPlayer()).get();
        
        playerLabel.setText(player.getNome());
        
        if(player.getRuolo() == PORTIERE.getRoleString()) {
            goalkeeperButton.setDisable(false);
        } else if(player.getRuolo() == DIFENSORE.getRoleString()) {
            defenderButton.setDisable(false);
        } else if(player.getRuolo() == CENTROCAMPISTA.getRoleString()) {
            midfielderButton.setDisable(false);
        } else {
            strikerButton.setDisable(false);
        }
        
        lastBetLabel.setText("Last Bet: ");

        timeLeftLabel.setText("Time Left: ");
        
        betLabel.textProperty().bind(Bindings.format("%.0f $", betSlider.valueProperty()));
        
        cancelButton.setOnAction(e -> Views.loadTeamScene());
        
        betButton.setOnAction(e -> Views.loadTeamScene());
    }
}
