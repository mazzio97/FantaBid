package org.fantabid.controller;

import java.sql.Date;

import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Buttons;
import org.fantabid.view.Dialogs;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LeaguesController {

    @FXML private VBox leaguesBox;
    @FXML private Button createLeagueButton;
    @FXML private Button refreshButton;
    @FXML private Button backButton;
    private final Model model = Model.get();

    public final void initialize() {
        refreshButton.setGraphic(Buttons.REFRESH_BUTTON_GRAPHIC);
        fillLeaguesBox();
        
        createLeagueButton.setOnAction(e -> Views.loadNewLeagueScene());
        refreshButton.setOnAction(e -> fillLeaguesBox());
        backButton.setOnAction(e -> Views.loadUserAreaScene());
    }
    
    private void fillLeaguesBox() {
        leaguesBox.getChildren().clear();
        Queries.getNotRegisteredLeagues(model.getUser().getUsername())
               .map(r -> {
                   Button b = Buttons.listButton(r.getNomecampionato() + ", closing at: " + r.getDatachiusura());
                   b.setOnAction(e -> {
                       if (new Date(System.currentTimeMillis()).after(r.getDatachiusura())) {
                           Dialogs.showErrorDialog("League Expired", "You can't signup to this league anymore.");
                       } else if (r.getNumeromassimosquadre() != null && r.getNumeromassimosquadre() == Queries.numTeamsInLeague(r.getIdcampionato())) {
                           Dialogs.showErrorDialog("Max Teams Reached", "You can't signup to this league because the maximum number of teams has been reached.");
                       } else {
                           model.setLeague(r);
                           Views.loadLeagueSignupScene();
                       }
                   });
                   return b;
               })
               .forEach(leaguesBox.getChildren()::add);
    }
}
