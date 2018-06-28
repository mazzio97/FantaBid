package org.fantabid.controller;

import java.util.stream.Collectors;

import org.fantabid.utils.Model;
import org.fantabid.utils.Queries;
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
        
        leaguesBox.getChildren().addAll(Queries.getLeagueFromUser(model.getUser())
                                               .map(Buttons::listButton)
                                               .peek(b -> b.setOnAction(e -> {
                                                   model.setLeague(b.getText());
                                                   Views.loadLeagueScene();
                                               }))
                                               .collect(Collectors.toList()));
        
        findLeagueButton.setOnAction(e -> Views.loadLeaguesScene());
        
        backButton.setOnAction(e -> {
            model.removeUser();
            Views.loadLoginScene();
        });
    }
}
