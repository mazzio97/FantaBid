package org.fantabid.controller;

import java.util.stream.Collectors;

import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Buttons;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LeaguesController {

    @FXML private VBox leaguesBox;
    @FXML private Button createLeagueButton;
    @FXML private Button backButton;
    private final Model model = Model.get();

    public final void initialize() {
        
        leaguesBox.getChildren().addAll(Queries.getOpenLeagues()
                                               .map(Buttons::listButton)
                                               .peek(b -> b.setOnAction(e -> {
                                                   model.setLeague(b.getText());
                                                   Views.loadLeagueScene();
                                               }))
                                               .collect(Collectors.toList()));
        
        createLeagueButton.setOnAction(e -> Views.loadNewLeagueScene());
        
        backButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}
