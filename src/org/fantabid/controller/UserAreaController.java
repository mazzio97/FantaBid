package org.fantabid.controller;

import static org.fantabid.generated.Tables.*;

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
        usernameLabel.setText(model.getUser());
        
        Queries.getTeamsFromUser(model.getUser())
               .map(r -> {
                   Button b = Buttons.listButton(r.getValue(SQUADRA.NOMESQUADRA) +
                                                 " (" + r.getValue(CAMPIONATO.NOME) + "), " +
                                                 "closing at: " + r.getValue(CAMPIONATO.DATACHIUSURA));
                   b.setOnAction(e -> {
                       model.setLeague(r.getValue(CAMPIONATO.IDCAMPIONATO));
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
