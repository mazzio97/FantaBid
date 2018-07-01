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
    
    public final void initialize() {
        leagueLabel.setText("League Name");
        teamLabel.setText("Team Name");
        
        roleComboBox.getItems().addAll(Role.values());
        roleComboBox.getSelectionModel().select(0);
        
        teamComboBox.getItems().add(ANY);
        Queries.getAllTeams().forEach(teamComboBox.getItems()::add);
        teamComboBox.getSelectionModel().select(0);
        
        TableColumn<CalciatoreRecord, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("name"));
        playersTable.getColumns().add(nameCol);
        TableColumn<CalciatoreRecord, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("role"));
        playersTable.getColumns().add(roleCol);
        TableColumn<CalciatoreRecord, String> teamCol = new TableColumn<>("Team");
        teamCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, String>("team"));
        playersTable.getColumns().add(teamCol);
        TableColumn<CalciatoreRecord, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<CalciatoreRecord, Integer>("price"));
        playersTable.getColumns().add(priceCol);                
        
        playersTable.setItems(filteredPlayers);

        addButton.setOnAction(e -> Views.loadBetInfoScene());
        updatePlayersButton.setOnAction(e -> {
            filteredPlayers.clear();
            Queries.filterPlayers(playerFilterField.getText(),
                    roleComboBox.getSelectionModel().getSelectedItem().getRoleString(),
                    Optional.of(teamComboBox.getSelectionModel().getSelectedItem())
                            .filter(ANY::equals)
                            .orElse(null))
            .forEach(filteredPlayers::add);

        });
        backButton.setOnAction(e -> Views.loadLeaguesScene());
        updatePlayersButton.fire();
    }

}
