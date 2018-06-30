package org.fantabid.controller;

import org.fantabid.entities.Player;
import org.fantabid.model.Queries;
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

public class LeagueController {

    private static final String ALL_AVAILABLE_OPTIONS = "Any";

    @FXML private Label leagueLabel;
    @FXML private Label teamLabel;
    @FXML private TextField playerFilterField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private ComboBox<String> teamComboBox;
    @FXML private TableView<Player> playersTable;
    @FXML private TableView<?> teamTable;
    @FXML private Button updatePlayersButton;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button backButton;

    private final ObservableList<Player> filteredPlayers = FXCollections.observableArrayList();
    
    public final void initialize() {
        leagueLabel.setText("League Name");
        teamLabel.setText("Team Name");
        roleComboBox.getItems().addAll(ALL_AVAILABLE_OPTIONS, "P", "D", "C", "A");
        roleComboBox.getSelectionModel().select(0);
        teamComboBox.getItems().add(ALL_AVAILABLE_OPTIONS);
        teamComboBox.getItems().addAll(Queries.getAllTeams());
        teamComboBox.getSelectionModel().select(0);
        
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        playersTable.getColumns().add(nameCol);
        TableColumn<Player, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<Player, String>("role"));
        playersTable.getColumns().add(roleCol);
        TableColumn<Player, String> teamCol = new TableColumn<>("Team");
        teamCol.setCellValueFactory(new PropertyValueFactory<Player, String>("team"));
        playersTable.getColumns().add(teamCol);
        TableColumn<Player, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("price"));
        playersTable.getColumns().add(priceCol);                
        
        playersTable.setItems(filteredPlayers);

        addButton.setOnAction(e -> Views.loadBetInfoScene());
        updatePlayersButton.setOnAction(e -> {
            filteredPlayers.clear();
            filteredPlayers.addAll(Queries.filterPlayers(playerFilterField.getText(),
                                                         roleComboBox.getSelectionModel().getSelectedItem(),
                                                         teamComboBox.getSelectionModel().getSelectedItem()));
        });
        backButton.setOnAction(e -> Views.loadLeaguesScene());
        updatePlayersButton.fire();
    }

}
