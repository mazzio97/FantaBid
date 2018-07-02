package org.fantabid.controller;

import java.awt.TextField;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.RegolaRecord;
import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
        CampionatoRecord league = Queries.getLeague(model.getLeague()).get();
        Stream<RegolaRecord> rules = Queries.getRulesFromLeague(model.getLeague());
        
        nameLabel.setText(league.getNome());
        
        descriptionLabel.setText("Type: " + league.getTipoasta() + "\n" +
                                 "Closure Date: " + league.getDatachiusura() + "\n" +
                                 "Initial Budget:" + league.getBudgetpersquadra());
        
        rulesLabel.setText("Rules:\n- " + rules.map(RegolaRecord::getNome).collect(Collectors.joining("\n- ")));
        
        registerButton.setOnAction(e -> {
            Queries.registerTeam(league.getIdcampionato(), model.getUser(), teamNameField.getText(),
                                 league.getBudgetpersquadra());
            Views.loadUserAreaScene();
        });
        
        cancelButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}