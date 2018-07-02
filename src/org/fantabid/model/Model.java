package org.fantabid.model;

import java.util.Optional;

public final class Model {
    
    private static final Model SINGLETON = new Model();

    private Optional<String> currentUser = Optional.empty();
    private Optional<Integer> currentTeam = Optional.empty();
    private Optional<Integer> currentLeague = Optional.empty();
    private Optional<Integer> currentPlayer = Optional.empty();
    
    public static Model get() {
        return SINGLETON;
    }

    public String getUser() {
        return currentUser.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setUser(String username) {
        currentUser = Optional.of(username);
    }
    
    public void removeUser() {
        currentUser = Optional.empty();
    }
    
    public int getTeam() {
        return currentTeam.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setTeam(int teamId) {
        currentTeam = Optional.of(teamId);
    }
    
    public void removeTeam() {
        currentTeam = Optional.empty();
    }
    
    public int getLeague() {
        return currentLeague.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setLeague(int leagueId) {
        currentLeague = Optional.of(leagueId);
    }
    
    public void removeLeague() {
        currentLeague = Optional.empty();
    }
    
    public int getPlayer() {
        return currentPlayer.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setPlayer(int playerId) {
        currentPlayer = Optional.of(playerId);
    }
    
    public void removePlayer() {
        currentPlayer = Optional.empty();
    }
}
