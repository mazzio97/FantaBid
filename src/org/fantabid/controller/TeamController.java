package org.fantabid.controller;

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

    private final ObservableList<CalciatoreRecord> filteredPlayers = FXCollections.observableArrayList();
    private final ObservableList<CalciatoreRecord> teamPlayers = FXCollections.observableArrayList(
                                                                     Queries.getAllPlayersOfTeam(model.getTeam()
                                                                                                      .getIdsquadra())
                                                                            .collect(Collectors.toSet())
                                                                 );
    
    public final void initialize() {
        leagueLabel.setText(model.getLeague().getNomecampionato());
        teamLabel.setText(model.getTeam().getNomesquadra());
        roleComboBox.getItems().addAll(Role.values());
        roleComboBox.getSelectionModel().select(0);
        teamComboBox.getItems().add(ANY);
        Queries.getAllRealTeams().forEach(teamComboBox.getItems()::add);
        teamComboBox.getSelectionModel().select(0);
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
        /*
         * Insert data into tables
         */
        filterPlayers();
        playersTable.setItems(filteredPlayers);
        teamTable.setItems(teamPlayers);
        updateTeamBudget();        
        /*
         * Buttons event handler
         */
        backButton.setOnAction(e -> {
            model.removeTeam();
            model.removeLeague();
            Views.loadUserAreaScene();
        });
        playerFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterPlayers());
        roleComboBox.setOnAction(e -> filterPlayers());
        teamComboBox.setOnAction(e -> filterPlayers());
        /*
         * League-specific view and handlers
         */
        if (model.getLeague().getAstarialzo()) {
            biddifyView();
            addButton.setOnAction(e -> {
                model.setPlayer(playersTable.getSelectionModel().getSelectedItem());
                Views.loadBetInfoScene();
                Optional.ofNullable(model.getPlayer()).ifPresent(c -> {
                    teamPlayers.add(c);
                    model.removePlayer();
                });
                filterPlayers();
            });
            removeButton.setOnAction(e -> System.out.println("View all the previous bets!"));
        } else {
            addButton.setOnAction(e -> {
                addPlayerToTeam(playersTable.getSelectionModel().getSelectedItem());
                filterPlayers();
                updateTeamBudget();
            });
            removeButton.setOnAction(e -> {
                CalciatoreRecord toRemove = teamTable.getSelectionModel().getSelectedItem();
                Queries.removePlayerFromTeam(model.getTeam().getIdsquadra(), toRemove.getIdcalciatore());
                Queries.updateBudgetLeft(model.getTeam().getIdsquadra(), toRemove.getPrezzostandard());
                teamPlayers.remove(toRemove);
                filterPlayers();
                updateTeamBudget();
            });
        }
    }
    
    private void biddifyView() {
        playersTable.getColumns().remove(3);
        teamTable.getColumns().remove(3);
        addButton.setText(">> BET");
        removeButton.setText("INFO"); // TODO: Storico delle puntate su quel giocatore
    }

    private void addPlayerToTeam(CalciatoreRecord c) {
        final Role r = Role.fromString(c.getRuolo());
        final int remainingBudget = model.getTeam().getCreditoresiduo();
        final int remainingPlayers = Role.ANY.getMaxInTeam() - (teamPlayers.size() + 1);
        Optional.of(teamPlayers)
                .filter(tp -> tp.size() < Role.ANY.getMaxInTeam())
                .filter(tp -> remainingBudget >= c.getPrezzostandard() + remainingPlayers)
                .filter(tp -> tp.stream().filter(p -> p.getRuolo().equals(c.getRuolo())).count() < r.getMaxInTeam())
//                .filter(tp -> !tp.contains(c))
                .ifPresent(tp -> {
                    tp.add(c);
                    Queries.insertPlayerIntoTeam(model.getTeam().getIdsquadra(), c.getIdcalciatore());
                    Queries.updateBudgetLeft(model.getTeam().getIdsquadra(), -c.getPrezzostandard()); 
                });
    }

    private void filterPlayers() {
        String namePart = Optional.ofNullable(playerFilterField.getText()).map(String::toUpperCase).orElse("");
        String roleFilter = roleComboBox.getSelectionModel().getSelectedItem().getRoleString();
        String teamFilter = Optional.of(teamComboBox.getSelectionModel().getSelectedItem())
                                    .filter(r -> !r.equals(ANY))
                                    .orElse(null);
        filteredPlayers.clear();
        model.getAllPlayers().stream()
                             .filter(c -> c.getNome().toUpperCase().contains(namePart))
                             .filter(c -> roleFilter == null || c.getRuolo().equals(roleFilter))
                             .filter(c -> teamFilter == null || c.getSquadra().equals(teamFilter))
                             .filter(c -> !teamPlayers.contains(c))
                             .forEach(filteredPlayers::add);
    }

    private void updateTeamBudget() {
        budgetLabel.setText(String.valueOf(Queries.getTeam(model.getTeam().getIdsquadra())
                                                  .map(s -> s.getCreditoresiduo())
                                                  .orElse((short) 0) + "M"));
    }
}
