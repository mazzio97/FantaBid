package org.fantabid.utils;

import java.util.Optional;

public final class Model {
    
    private static final Model SINGLETON = new Model();

    private Optional<String> currentUser = Optional.empty();
    private Optional<String> currentTeam = Optional.empty();
    private Optional<String> currentLeague = Optional.empty();
    
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
    
    public String getTeam() {
        return currentTeam.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setTeam(String team) {
        currentTeam = Optional.of(team);
    }
    
    public void removeTeam() {
        currentTeam = Optional.empty();
    }
    
    public String getLeague() {
        return currentLeague.orElseThrow(() -> new IllegalStateException());
    }
    
    public void setLeague(String league) {
        currentLeague = Optional.of(league);
    }
    
    public void removeLeague() {
        currentLeague = Optional.empty();
    }
}
