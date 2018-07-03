package org.fantabid.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.RegolaRecord;
import org.fantabid.model.LeagueType;
import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.model.utils.Limits;
import org.fantabid.view.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LeagueSignupController {
    
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label infoLabel;
    @FXML private Label rulesLabel;
    @FXML private TextField teamNameField;
    @FXML private Button registerButton;
    @FXML private Button cancelButton;
    private final Model model = Model.get();

    public final void initialize() {
        teamNameField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_TEAM_NAME_CHARS));
        registerButton.disableProperty().bind(new BooleanBinding() {{
                super.bind(teamNameField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return teamNameField.getText().isEmpty();
            }
        });
        
        CampionatoRecord league = model.getLeague();
        Stream<RegolaRecord> rules = Queries.getRulesFromLeague(league.getIdcampionato());
        
        nameLabel.setText(league.getNomecampionato());
        
        descriptionLabel.setText("Type: " + (league.getAstarialzo() ? LeagueType.BID : LeagueType.CLASSIC) + "\n" +
                                 "Closure Date: " + league.getDatachiusura() + "\n" +
                                 "Initial Budget:" + league.getBudgetpersquadra());
        
        rulesLabel.setText("Rules:\n- " + rules.map(RegolaRecord::getNome).collect(Collectors.joining("\n- ")));
        
        registerButton.setOnAction(e -> {
            Queries.registerTeam(league.getIdcampionato(), model.getUser().getUsername(),
                                 teamNameField.getText(), league.getBudgetpersquadra());
            model.removeLeague();
            Views.loadUserAreaScene();
        });
        
        cancelButton.setOnAction(e -> {
            model.removeLeague();
            Views.loadUserAreaScene();
        });
    }
}