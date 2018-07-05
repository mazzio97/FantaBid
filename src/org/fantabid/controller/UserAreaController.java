package org.fantabid.controller;

import java.sql.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.model.utils.Pair;
import org.fantabid.view.Buttons;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class UserAreaController {
    
    @FXML private Label usernameLabel;
    @FXML private VBox leaguesBox;
    @FXML private Button findLeagueButton;
    @FXML private Button refreshButton;
    @FXML private Button backButton;
    private final Model model = Model.get();
    
    public final void initialize() {
        usernameLabel.setText(model.getUser().getUsername());
        refreshButton.setGraphic(Buttons.REFRESH_BUTTON_GRAPHIC);
        fillLeaguesBox();
        
        findLeagueButton.setOnAction(e -> Views.loadLeaguesScene());
        refreshButton.setOnAction(e -> fillLeaguesBox());
        backButton.setOnAction(e -> {
            model.removeUser();
            Views.loadLoginScene();
        });
    }
    
    private void fillLeaguesBox() {
        leaguesBox.getChildren().clear();
        
        Map<Boolean, Stream<Button>> leagues;
        leagues = Queries.getTeamsFromUser(model.getUser().getUsername())
                          .map(r -> {
                              Button b = Buttons.listButton(r.getFirst().getNomesquadra() +
                                                            " (" + r.getSecond().getNomecampionato() + "), " +
                                                            "closing at: " + r.getSecond().getDatachiusura());
                              b.setOnAction(e -> {
                                  model.setTeam(r.getFirst());
                                  model.setLeague(r.getSecond());
                                  Views.loadTeamScene();
                              });
                              return Pair.of(r.getSecond().getDatachiusura(), b);
                          })
                          .sorted((p1, p2) -> p1.getFirst().after(p2.getFirst()) ? -1 : 1)
                          .collect(Collectors.groupingBy(p -> p.getFirst()
                                                               .before(new Date(System.currentTimeMillis()))))
                          .entrySet()
                          .stream()
                          .map(e -> Pair.of(e.getKey(), e.getValue().stream().map(Pair::getSecond)))
                          .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        
        leagues.getOrDefault(false, Stream.empty()).sorted((b1, b2) -> -1).forEach(leaguesBox.getChildren()::add);
        leaguesBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
        leagues.getOrDefault(true, Stream.empty()).forEach(leaguesBox.getChildren()::add);
    }
}
