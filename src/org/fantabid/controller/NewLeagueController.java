package org.fantabid.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.RegolaRecord;
import org.fantabid.model.LeagueType;
import org.fantabid.model.Limits;
import org.fantabid.model.Queries;
import org.fantabid.view.Views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    @FXML private ChoiceBox<LeagueType> leagueType;
    @FXML private HBox numTeamsBox;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker endingDatePicker;
    @FXML private VBox rulesBox;
    @FXML private HBox teamBudgetBox;
    @FXML private Label teamBudgetLabel;
    @FXML private Slider teamBudgetSlider;
    @FXML private Button cancelButton;
    @FXML private Button createButton;

    public final void initialize() {        
        nameField.setTextFormatter(Limits.getTextFormatter(Limits.MAX_LEAGUE_NAME_CHARS));
        descriptionArea.setTextFormatter(Limits.getTextFormatter(Limits.MAX_LEAGUE_DESCRIPTION_CHARS));
        createButton.disableProperty().bind(new BooleanBinding() {{
                super.bind(nameField.textProperty(), endingDatePicker.valueProperty());
            }
    
            @Override
            protected boolean computeValue() {
                return nameField.getText().isEmpty() || endingDatePicker.getValue() == null;
            }
        });
        endingDatePicker.setDayCellFactory(p -> new DateCell() {
            
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now()));
            }
        });

        Arrays.asList(LeagueType.values()).forEach(leagueType.getItems()::add);
        leagueType.getSelectionModel().select(LeagueType.BID);

        Spinner<Integer> numTeamsSpinner = new Spinner<>(MIN_NUM_TEAMS, MAX_NUM_TEAMS, DEFAULT_NUM_TEAMS);
        numTeamsBox.getChildren().add(numTeamsSpinner);

        Map<CheckBox, RegolaRecord> rulesMap = new LinkedHashMap<>();
        Queries.getRules().forEach(r -> rulesMap.put(new CheckBox(r.getNome()), r));
        rulesBox.getChildren().addAll(rulesMap.keySet());

        leagueType.setOnAction(e -> {
            numTeamsBox.setVisible(leagueType.getSelectionModel().getSelectedItem().equals(LeagueType.BID));
            teamBudgetBox.setVisible(leagueType.getSelectionModel().getSelectedItem().equals(LeagueType.BID));
        });

        teamBudgetSlider.setSnapToTicks(true);
        teamBudgetLabel.textProperty().bind(Bindings.format("Budget: %.0f$", teamBudgetSlider.valueProperty()));

        createButton.setOnAction(e -> {
            CampionatoRecord league = Queries.registerLeague(nameField.getText(),
                    descriptionArea.getText(),
                    Double.valueOf(teamBudgetSlider.getValue()).intValue(),
                    new Date(System.currentTimeMillis()),
                    Date.valueOf(endingDatePicker.getValue()),
                    leagueType.getValue().isBid(),
                    numTeamsSpinner.getValue());
            rulesBox.getChildren().stream()
            .map(n -> (CheckBox) n)
            .filter(CheckBox::isSelected)
            .map(rulesMap::get)
            .map(RegolaRecord::getIdregola)
            .forEach(i -> Queries.linkRuleToLeague(i, league.getIdcampionato()));
        });

        cancelButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}