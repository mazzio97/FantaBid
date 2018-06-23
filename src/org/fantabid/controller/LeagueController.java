
package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LeagueController {

    @FXML private Label leagueLabel;
    @FXML private Label teamLabel;
    @FXML private TextField playerFilterField;
    @FXML private TableView<?> playersTable;
    @FXML private TableView<?> teamTable;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button backButton;

    public final void initialize() {
        leagueLabel.setText("League Name");
        teamLabel.setText("Team Name");
        addButton.setOnAction(e -> Views.loadBetInfoScene());
        backButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}
