package org.fantabid.controller;

import org.fantabid.view.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewLeagueController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionField;
    @FXML private CheckBox rulePlaceholder;
    @FXML private Button createButton;
    
    public final void initialize() {
        createButton.setOnAction(e -> Views.loadUserAreaScene());
    }
}