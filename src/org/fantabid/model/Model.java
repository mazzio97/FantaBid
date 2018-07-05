package org.fantabid.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.fantabid.generated.tables.records.AllenatoreRecord;
import org.fantabid.generated.tables.records.CalciatoreRecord;
import org.fantabid.generated.tables.records.CampionatoRecord;
import org.fantabid.generated.tables.records.SquadraRecord;

public final class Model {
    
    private static final Model SINGLETON = new Model();
    private final List<CalciatoreRecord> allPlayers = Queries.getAllPlayers().collect(Collectors.toList());
    private Optional<AllenatoreRecord> currentUser = Optional.empty();
    private Optional<SquadraRecord> currentTeam = Optional.empty();
    private Optional<CampionatoRecord> currentLeague = Optional.empty();
    private Optional<CalciatoreRecord> currentPlayer = Optional.empty();

    public static Model get() {
        return SINGLETON;
    }

    public AllenatoreRecord getUser() {
        return currentUser.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setUser(AllenatoreRecord username) {
        currentUser = Optional.of(username);
    }
    
    public void removeUser() {
        currentUser = Optional.empty();
    }
    
    public SquadraRecord getTeam() {
        return currentTeam.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setTeam(SquadraRecord team) {
        currentTeam = Optional.of(team);
    }
    
    public void removeTeam() {
        currentTeam = Optional.empty();
    }
    
    public CampionatoRecord getLeague() {
        return currentLeague.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setLeague(CampionatoRecord league) {
        currentLeague = Optional.of(league);
    }
    
    public void removeLeague() {
        currentLeague = Optional.empty();
    }
    
    public CalciatoreRecord getPlayer() {
        return currentPlayer.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setPlayer(CalciatoreRecord player) {
        currentPlayer = Optional.of(player);
    }
    
    public void removePlayer() {
        currentPlayer = Optional.empty();
    }
    
    public Collection<CalciatoreRecord> getAllPlayers() {
        return this.allPlayers;
    }
}
