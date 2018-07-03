package org.fantabid.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.model.Model;
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
    @FXML private Label budgetLabel;
    @FXML private TextField playerFilterField;
    @FXML private ComboBox<Role> roleComboBox;
    @FXML private ComboBox<String> teamComboBox;
    @FXML private TableView<CalciatoreRecord> playersTable;
    @FXML private TableView<CalciatoreRecord> teamTable;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button backButton;
    private final Model model = Model.get();

    private final List<CalciatoreRecord> allPlayers = Queries.getAllPlayers().collect(Collectors.toList());
    private final ObservableList<CalciatoreRecord> filteredPlayers = FXCollections.observableArrayList();
    private final ObservableList<CalciatoreRecord> teamPlayers = FXCollections.observableArrayList();
    
    public final void initialize() {
        leagueLabel.setText("League Name");
        teamLabel.setText("Team Name");
        
        roleComboBox.getItems().addAll(Role.values());
        roleComboBox.getSelectionModel().select(0);
        
        teamComboBox.getItems().add(ANY);
        Queries.getAllRealTeams().forEach(teamComboBox.getItems()::add);
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
        addButton.setOnAction(e -> {
            addPlayerToTeam(playersTable.getSelectionModel().getSelectedItem());
            budgetLabel.setText(String.valueOf(model.getTeam().getCreditoresiduo() - budgetSpent()) + "M");
        });
        removeButton.setOnAction(e -> {
            teamPlayers.remove(teamTable.getSelectionModel().getSelectedItem());
            budgetLabel.setText(String.valueOf(model.getTeam().getCreditoresiduo() - budgetSpent()) + "M");
        });
        
        backButton.setOnAction(e -> {
            model.removeTeam();
            model.removeLeague();
            Views.loadUserAreaScene();
        });
       
        playerFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterPlayers());
        roleComboBox.setOnAction(e -> filterPlayers());
        teamComboBox.setOnAction(e -> filterPlayers());
        filterPlayers();
    }
    
    private void addPlayerToTeam(CalciatoreRecord c) {
        final Role r = Role.fromString(c.getRuolo());
        final int remainingBudget = model.getTeam().getCreditoresiduo() - budgetSpent();
        final int remainingPlayers = Role.ANY.getMaxInTeam() - (teamPlayers.size() + 1);
        Optional.of(teamPlayers)
                .filter(tp -> tp.size() < Role.ANY.getMaxInTeam())
                .filter(tp -> remainingBudget >= c.getPrezzostandard() + remainingPlayers)
                .filter(tp -> tp.stream().filter(p -> p.getRuolo().equals(c.getRuolo())).count() < r.getMaxInTeam())
                .filter(tp -> !tp.contains(c))
                .ifPresent(tp -> tp.add(c));
    }
    
    private int budgetSpent() {
        return teamPlayers.stream()
                          .map(CalciatoreRecord::getPrezzostandard)
                          .mapToInt(p -> p.intValue())
                          .sum();
    }
    
    private void filterPlayers() {
        String namePart = Optional.ofNullable(playerFilterField.getText()).map(String::toUpperCase).orElse("");
        String roleFilter = roleComboBox.getSelectionModel().getSelectedItem().getRoleString();
        String teamFilter = Optional.of(teamComboBox.getSelectionModel().getSelectedItem())
                                    .filter(r -> !r.equals(ANY))
                                    .orElse(null);
        filteredPlayers.clear();
        allPlayers.stream()
                  .filter(c -> c.getNome().toUpperCase().contains(namePart))
                  .filter(c -> roleFilter == null || c.getRuolo().equals(roleFilter))
                  .filter(c -> teamFilter == null || c.getSquadra().equals(teamFilter))
                  .forEach(filteredPlayers::add);
    }

}
