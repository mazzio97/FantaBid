package org.fantabid.controller;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.model.Model;
import org.fantabid.model.Queries;
import org.fantabid.model.Role;
import org.fantabid.view.Buttons;
import org.fantabid.view.Dialogs;
import org.fantabid.view.Views;

import javafx.beans.binding.Bindings;
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
    @FXML private Button refreshButton;
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
        refreshButton.setGraphic(Buttons.REFRESH_BUTTON_GRAPHIC);
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
        updateTeamBudgetLabel();        
        /*
         * Common buttons event handlers and bindings
         */
        backButton.setOnAction(e -> {
            model.removeTeam();
            model.removeLeague();
            Views.loadUserAreaScene();
        });
        playerFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterPlayers());
        roleComboBox.setOnAction(e -> filterPlayers());
        teamComboBox.setOnAction(e -> filterPlayers());
        teamTable.setOnMouseClicked(e -> playersTable.getSelectionModel().clearSelection());
        playersTable.setOnMouseClicked(e -> teamTable.getSelectionModel().clearSelection());
        addButton.disableProperty().bind(Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems()));
        /*
         * League-specific view and handlers
         */
        if (Queries.getLeague(model.getLeague().getIdcampionato())
                   .map(c -> c.getDatachiusura())
                   .orElse(null)
                   .before(new Date(System.currentTimeMillis()))) {
            addButton.setDisable(true);
            removeButton.setDisable(true);
        }
        if (model.getLeague().getAstarialzo()) {
            biddifyView();
            removeButton.setDisable(false); // It must be always possible to view the history
            addButton.setOnAction(e -> addPlayerToTeamBidLeague(playersTable.getSelectionModel().getSelectedItem()));
            removeButton.disableProperty().bind(Bindings.and(Bindings.isEmpty(teamTable.getSelectionModel().getSelectedItems()), 
                                                             Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems())));
            removeButton.setOnAction(e -> {
                model.setPlayer(Optional.ofNullable(teamTable.getSelectionModel().getSelectedItem())
                                        .orElse(playersTable.getSelectionModel().getSelectedItem()));
                Views.loadBetHistoryScene();
            });
            refreshButton.setOnAction(e -> {
                teamPlayers.clear();
                teamPlayers.addAll(Queries.getAllPlayersOfTeam(model.getTeam().getIdsquadra())
                                          .collect(Collectors.toSet()));
                filterPlayers();
                updateTeamBudgetLabel();
            });
        } else {
            addButton.setOnAction(e -> addPlayerToTeamClassicLeague(playersTable.getSelectionModel().getSelectedItem()));
            removeButton.disableProperty().bind(Bindings.isEmpty(teamTable.getSelectionModel().getSelectedItems()));
            removeButton.setOnAction(e -> removePlayerFromTeamClassicLeague(teamTable.getSelectionModel().getSelectedItem()));
            refreshButton.setVisible(false);
        }
    }
    
    private void biddifyView() {
        playersTable.getColumns().remove(3);
        teamTable.getColumns().remove(3);
        addButton.setText(">> BET");
        removeButton.setText("HISTORY");
    }

    private void addPlayerToTeamClassicLeague(CalciatoreRecord c) {
        final Role r = Role.fromString(c.getRuolo());
        final int remainingBudget = model.getTeam().getCreditoresiduo();
        final int remainingPlayers = Role.ANY.getMaxInTeam() - (teamPlayers.size() + 1);
        boolean canAddPlayer = Optional.of(teamPlayers)
                                       .filter(tp -> tp.size() < Role.ANY.getMaxInTeam())
                                       .filter(tp -> remainingBudget >= c.getPrezzostandard() + remainingPlayers)
                                       .filter(tp -> tp.stream().filter(p -> p.getRuolo().equals(c.getRuolo())).count() < r.getMaxInTeam())
                                       .isPresent();
        if (canAddPlayer) {
            teamPlayers.add(c);
            Queries.insertPlayerIntoTeam(model.getTeam().getIdsquadra(), c.getIdcalciatore());
            Queries.updateBudgetLeft(model.getTeam().getIdsquadra(), -c.getPrezzostandard());
        } else {
            Dialogs.showWarningDialog("Can't add player", "You don't have enough budget \n or already " + 
                                                          r.getMaxInTeam() + r.getRoleString() + " in your team.");
        }
        filterPlayers();
        updateTeamBudgetLabel();
    }

    private void removePlayerFromTeamClassicLeague(CalciatoreRecord c) {
        Queries.removePlayerFromTeam(model.getTeam().getIdsquadra(), c.getIdcalciatore());
        Queries.updateBudgetLeft(model.getTeam().getIdsquadra(), c.getPrezzostandard());
        teamPlayers.remove(c);
        filterPlayers();
        updateTeamBudgetLabel();
    }

    private void addPlayerToTeamBidLeague(CalciatoreRecord c) {
        final Role r = Role.fromString(c.getRuolo());
        boolean canAddPlayer = Optional.of(teamPlayers)
                                       .filter(tp -> tp.size() < Role.ANY.getMaxInTeam())
                                       .filter(tp -> tp.stream().filter(p -> p.getRuolo().equals(c.getRuolo())).count() < r.getMaxInTeam())
                                       .isPresent();
        if (canAddPlayer) {
            model.setPlayer(c);
            Views.loadBetInfoScene();
            Optional.ofNullable(model.getPlayer()).ifPresent(c1 -> {
                teamPlayers.add(c1);
                model.removePlayer();
            });
        } else {
            Dialogs.showWarningDialog("Can't add player", "You already have" + 
                                                          r.getMaxInTeam() + r.getRoleString() + " in your team.");
        }
        filterPlayers();
        updateTeamBudgetLabel();
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

    private void updateTeamBudgetLabel() {
        budgetLabel.setText(String.valueOf(Queries.getTeam(model.getTeam().getIdsquadra())
                                                  .map(s -> s.getCreditoresiduo())
                                                  .orElse((short) 0) + "M"));
    }
}
