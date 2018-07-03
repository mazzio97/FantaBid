package org.fantabid.controller;

import static org.fantabid.model.Role.*;

import java.util.Date;

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
        PuntataRecord lastBet = Queries.getLastBet(league.getIdcampionato(), player.getIdcalciatore())
                                       .orElse(new PuntataRecord("giampaolo", league.getIdcampionato(), 
                                                                 (short) player.getIdcalciatore(), (short) 0,
                                                                 (short) 0, "", 0, (short) 0,
                                                                 league.getDatachiusura()));
        
        playerLabel.setText(player.getNome() + " (" + player.getSquadra() + ")");
        lastBetLabel.setText("Last Bet: " + lastBet.getValore() + "$ (" + lastBet.getUsername() + ")");
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
        
        betLabel.textProperty().bind(Bindings.format("%.0f $", betSlider.valueProperty()));
        betSlider.setMin(lastBet.getValore());
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
