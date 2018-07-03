package org.fantabid.controller;

import static org.fantabid.model.Role.*;

import java.util.Date;
import java.util.Optional;

import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.PuntataRecord;
import org.fantabid.generated.tables.records.SquadraRecord;
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
    @FXML private Label expiryDateLabel;
    @FXML private Slider betSlider;
    @FXML private Label betLabel;
    @FXML private Button cancelButton;
    @FXML private Button betButton;
    private final Model model = Model.get();
    
    public final void initialize() {        
        CalciatoreRecord player = model.getPlayer();
        SquadraRecord team = model.getTeam();
        CampionatoRecord league = model.getLeague();
        Optional<PuntataRecord> lastBet = Queries.getLastBet(league.getIdcampionato(), player.getIdcalciatore());
        
        playerLabel.setText(player.getNome() + " (" + player.getSquadra() + ")");
        lastBetLabel.setText("Last Bet: "
                             + lastBet.map(PuntataRecord::getValore).orElse((short) 0)
                             + "M ("
                             + lastBet.map(PuntataRecord::getIdsquadra)
                                      .map(Queries::getTeam)
                                      .map(Optional::get)
                                      .map(SquadraRecord::getUsername)
                                      .orElse("no one") + ")");
        expiryDateLabel.setText("Expiring At: " + new Date(league.getDatachiusura().getTime()));
        
        if(player.getRuolo() == PORTIERE.getRoleString()) {
            goalkeeperButton.setDisable(false);
        } else if(player.getRuolo() == DIFENSORE.getRoleString()) {
            defenderButton.setDisable(false);
        } else if(player.getRuolo() == CENTROCAMPISTA.getRoleString()) {
            midfielderButton.setDisable(false);
        } else {
            strikerButton.setDisable(false);
        }
        
        betLabel.textProperty().bind(Bindings.format("%.0fM", betSlider.valueProperty()));
        betSlider.setMin(lastBet.map(PuntataRecord::getValore).orElse((short) 0) + 1);
        // TODO: must depend on the remaining players in the team
        betSlider.setMax(team.getCreditoresiduo());
        
        cancelButton.setOnAction(e -> {
            model.removePlayer();
            Views.loadTeamScene();
        });
        
        betButton.setOnAction(e -> {
            model.removePlayer();
            Views.loadTeamScene();
        });
    }
}
