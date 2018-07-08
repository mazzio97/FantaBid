package org.fantabid.controller;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.RegolaRecord;
import org.fantabid.model.LeagueType;
import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.model.utils.Limits;
import org.fantabid.view.Dialogs;
import org.fantabid.view.Views;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LeagueSignupController {
    
    @FXML private Label nameLabel;
    @FXML private TextArea descriptionArea;
    @FXML private TextArea rulesArea;
    @FXML private TextArea infoArea;
    @FXML private TextField teamNameField;
    @FXML private Button registerButton;
    @FXML private Button cancelButton;
    private final Model model = Model.get();

    public final void initialize() {
        teamNameField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_TEAM_NAME_CHARS));
        registerButton.disableProperty().bind(Bindings.isEmpty(teamNameField.textProperty()));
        
        CampionatoRecord league = model.getLeague();
        Stream<RegolaRecord> rules = Queries.getRulesFromLeague(league.getIdcampionato());
        
        nameLabel.setText(league.getNomecampionato());
        
        descriptionArea.setText(league.getDescrizione());
        
        infoArea.setText("Type: " + (league.getAstarialzo() ? LeagueType.BID : LeagueType.CLASSIC) + "\n" +
                          "Closure Date: " + league.getDatachiusura() + "\n" +
                          "Initial Budget: " + league.getBudgetpersquadra());
        
        rulesArea.setText("Rules:\n- " + Optional.of(rules.map(RegolaRecord::getNome))
                                                  .map(r -> r.collect((Collectors.joining(";\n- "))))
                                                  .filter(s -> !s.isEmpty())
                                                  .orElse("None") + ".");
        
        registerButton.setOnAction(e -> {
            if (new Date(System.currentTimeMillis()).after(league.getDatachiusura())) {
                Dialogs.showErrorDialog("League Expired", "You can't signup to this league anymore.");
            } else {
                Queries.registerTeam(league.getIdcampionato(), model.getUser().getUsername(),
                                     teamNameField.getText(), league.getBudgetpersquadra());
                model.removeLeague();
                Views.loadUserAreaScene();
            }
        });
        
        cancelButton.setOnAction(e -> {
            model.removeLeague();
            Views.loadLeaguesScene();
        });
    }
}