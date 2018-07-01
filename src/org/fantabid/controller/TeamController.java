package org.fantabid.controller;

import java.util.Optional;

import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.model.Queries;
import org.fantabid.model.Role;
import org.fantabid.view.Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeamController {

    private static final String ANY = "Any";

    @FXML private Label leagueLabel;
    @FXML private Label teamLabel;
    @FXML private TextField playerFilterField;
    @FXML private ComboBox<Role> roleComboBox;
    @FXML private ComboBox<String> teamComboBox;
    @FXML private TableView<CalciatoreRecord> playersTable;
    @FXML private TableView<CalciatoreRecord> teamTable;
    @FXML private Button updatePlayersButton;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button backButton;

    private final ObservableList<CalciatoreRecord> filteredPlayers = FXCollections.observableArrayList();
    private final ObservableList<CalciatoreRecord> teamPlayers = FXCollections.observableArrayList();
    
    public final void initialize() {
        leagueLabel.setText("League Name");
        teamLabel.setText("Team Name");
        
        roleComboBox.getItems().addAll(Role.values());
        roleComboBox.getSelectionModel().select(0);
        
        teamComboBox.getItems().add(ANY);
        Queries.getAllTeams().forEach(teamComboBox.getItems()::add);
        teamComboBox.getSelectionModel().select(0);
        
        // TODO: create a method to add a column in a table
        /*
         * Create columns for playersTable
         */
        TableColumn<CalciatoreRecord, String> playersNameCol = new TableColumn<>("Name");
        playersNameCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("nome"));
        playersTable.getColumns().add(playersNameCol);
        TableColumn<CalciatoreRecord, String> playersRoleCol = new TableColumn<>("Role");
        playersRoleCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("ruolo"));
        playersTable.getColumns().add(playersRoleCol);
        TableColumn<CalciatoreRecord, String> playersTeamCol = new TableColumn<>("Team");
        playersTeamCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("squadra"));
        playersTable.getColumns().add(playersTeamCol);
        TableColumn<CalciatoreRecord, Integer> playersPriceCol = new TableColumn<>("Price");
        playersPriceCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, Integer>("prezzostandard"));
        playersTable.getColumns().add(playersPriceCol);
        /*
         * Create columns for teamTable
         */
        TableColumn<CalciatoreRecord, String> teamNameCol = new TableColumn<>("Name");
        teamNameCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("nome"));
        teamTable.getColumns().add(teamNameCol);
        TableColumn<CalciatoreRecord, String> teamRoleCol = new TableColumn<>("Role");
        teamRoleCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("ruolo"));
        teamTable.getColumns().add(teamRoleCol);
        TableColumn<CalciatoreRecord, String> teamTeamCol = new TableColumn<>("Team");
        teamTeamCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("squadra"));
        teamTable.getColumns().add(teamTeamCol);
        TableColumn<CalciatoreRecord, Integer> teamPriceCol = new TableColumn<>("Price");
        teamPriceCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, Integer>("prezzostandard"));
        teamTable.getColumns().add(teamPriceCol);

        playersTable.setItems(filteredPlayers);
        teamTable.setItems(teamPlayers);

//        addButton.setOnAction(e -> Views.loadBetInfoScene());
        addButton.setOnAction(e -> teamPlayers.add(playersTable.getSelectionModel().getSelectedItem()));
        removeButton.setOnAction(e -> teamPlayers.remove(teamTable.getSelectionModel().getSelectedItem()));
        updatePlayersButton.setOnAction(e -> {
            filteredPlayers.clear();
            Queries.filterPlayers(playerFilterField.getText(),
                                  roleComboBox.getSelectionModel().getSelectedItem().getRoleString(),
                                  Optional.of(teamComboBox.getSelectionModel().getSelectedItem())
                                                          .filter(r -> !r.equals(ANY))
                                                          .orElse(null))
                   .forEach(filteredPlayers::add);

        });
        backButton.setOnAction(e -> Views.loadLeaguesScene());
        updatePlayersButton.fire();
    }

}
