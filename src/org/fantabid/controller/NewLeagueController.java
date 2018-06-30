package org.fantabid.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.fantabid.entities.LeagueType;
import org.fantabid.model.Queries;
import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewLeagueController {

    public static final int MIN_NUM_TEAMS = 2;
    public static final int MAX_NUM_TEAMS = 12;
    public static final int DEFAULT_NUM_TEAMS = 8;
    
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> leagueType;
    @FXML private HBox numTeamsBox;
    @FXML private TextArea descriptionArea;
    @FXML private VBox rulesBox;
    @FXML private Slider teamBudgetSlider;
    @FXML private Button cancelButton;
    @FXML private Button createButton;
    
    public final void initialize() {
        leagueType.getItems().addAll(Arrays.asList(LeagueType.values()).stream().map(t -> t.name()).collect(Collectors.toList()));
        leagueType.getSelectionModel().select(1);
        Spinner<Integer> numTeamsSpinner = new Spinner<>(MIN_NUM_TEAMS, MAX_NUM_TEAMS, DEFAULT_NUM_TEAMS);
        numTeamsBox.getChildren().add(numTeamsSpinner);
        Queries.getRules().forEach(r -> rulesBox.getChildren().add(new CheckBox(r.getValue(1).toString())));
        leagueType.setOnAction(e -> {
            numTeamsSpinner.setDisable(leagueType.getSelectionModel().getSelectedIndex() == 0);
            teamBudgetSlider.setDisable(leagueType.getSelectionModel().getSelectedIndex() == 0);
        });
        createButton.setOnAction(e -> {
            System.out.println("Name: " + nameField.getText());
            System.out.println("Type: " + leagueType.getValue());
            System.out.println("Num of Teams: " + numTeamsSpinner.getValue());
            System.out.println("Rules: " + rulesBox.getChildren()
                                                   .stream()
                                                   .map(n -> (CheckBox) n)
                                                   .filter(c -> c.isSelected())
                                                   .map(c -> c.getText())
                                                   .collect(Collectors.joining(", ", "", "")));
            System.out.println("Description: " + descriptionArea.getText());
            System.out.println("Team Budget: " + Double.valueOf(teamBudgetSlider.getValue()).intValue());
        });
        cancelButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}