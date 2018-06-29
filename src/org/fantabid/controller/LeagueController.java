
package org.fantabid.controller;

import java.util.Optional;

import org.fantabid.entities.Player;
import org.fantabid.model.Queries;
import org.fantabid.view.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    @FXML private TableView<String> playersTable;
    @FXML private TableView<?> teamTable;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button backButton;

    public final void initialize() {
        leagueLabel.setText("League Name");
        teamLabel.setText("Team Name");
        roleComboBox.getItems().addAll(ALL_AVAILABLE_OPTIONS, "P", "D", "C", "A");
        roleComboBox.getSelectionModel().select(0);
        teamComboBox.getItems().add(ALL_AVAILABLE_OPTIONS);
        teamComboBox.getItems().addAll(Queries.getAllTeams());
        teamComboBox.getSelectionModel().select(0);
        
        ObservableList<String> filteredPlayers = FXCollections.observableArrayList(
                Queries.filterPlayers(Optional.of(playerFilterField.getText()),
                        Optional.of("P"),
                        Optional.of(teamComboBox.getSelectionModel().getSelectedItem())));
        playersTable.setItems(filteredPlayers);
        
        playersTable.getItems().forEach(i -> System.out.println(i.toString()));
        
        TableColumn<String, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue()));
        playersTable.getColumns().add(nameCol);

        addButton.setOnAction(e -> Views.loadBetInfoScene());
        backButton.setOnAction(e -> {
        });
    }
}
