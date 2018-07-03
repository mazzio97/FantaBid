package org.fantabid.controller;

import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Buttons;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class UserAreaController {
    
    @FXML private Label usernameLabel;
    @FXML private VBox leaguesBox;
    @FXML private Button findLeagueButton;
    @FXML private Button backButton;
    private final Model model = Model.get();
    
    public final void initialize() {
        usernameLabel.setText(model.getUser().getUsername());
        
        Queries.getTeamsFromUser(model.getUser().getUsername())
               .map(r -> {
                   Button b = Buttons.listButton(r.getFirst().getNomesquadra() +
                                                 " (" + r.getSecond().getNomecampionato() + "), " +
                                                 "closing at: " + r.getSecond().getDatachiusura());
                   b.setOnAction(e -> {
                       model.setTeam(r.getFirst());
                       model.setLeague(r.getSecond());
                       Views.loadTeamScene();
                   });
                   return b;
               })
               .forEach(leaguesBox.getChildren()::add);
        
        findLeagueButton.setOnAction(e -> Views.loadLeaguesScene());
        
        backButton.setOnAction(e -> {
            model.removeUser();
            Views.loadLoginScene();
        });
    }
}
