package org.fantabid.controller;

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

        Queries.getOpenLeagues()
               .map(r -> {
                   Button b = Buttons.listButton(r.getNome() + ", closing at: " + r.getDatachiusura());
                   b.setOnAction(e -> {
                       model.setLeague(r);
                       Views.loadTeamScene();
                   });
                   return b;
               })
               .forEach(leaguesBox.getChildren()::add);

        createLeagueButton.setOnAction(e -> Views.loadNewLeagueScene());

        backButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}
