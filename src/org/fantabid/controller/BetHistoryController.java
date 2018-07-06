package org.fantabid.controller;

import java.util.stream.Collectors;

import org.fantabid.generated.tables.records.SquadraRecord;
import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Buttons;
import org.fantabid.view.Labels;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

public class BetHistoryController {
    @FXML private Label playerLabel;
    @FXML private VBox betsBox;
    @FXML private Button backButton;
    @FXML private Button refreshButton;
    
    public final void initialize() {
        playerLabel.setText(Model.get().getPlayer().getNome());
        refreshButton.setGraphic(Buttons.REFRESH_BUTTON_GRAPHIC);
        refresh();
        backButton.setOnAction(e -> Views.loadTeamScene());
        refreshButton.setOnAction(e -> refresh());
    }
    
    private void refresh() {
        betsBox.getChildren().clear();
        betsBox.getChildren().addAll(Queries.getAllPlayerBets(Model.get().getPlayer().getIdcalciatore())
                                            .sorted((p1, p2) -> p1.getIdpuntata() > p2.getIdpuntata() ? -1 : 1)
                                            .map(p -> {
                                                SquadraRecord team = Queries.getTeam(p.getIdsquadra()).get();
                                                return Labels.listLabel(team.getNomesquadra() 
                                                                        + " (" + team.getUsername() + ") -> " 
                                                                        + p.getValore() + "M");
                                            })
                                            .collect(Collectors.toList()));
        if (betsBox.getChildren().isEmpty()) {
            betsBox.getChildren().add(Labels.listLabel("Any bet on this player yet!"));
        }
    }
}
