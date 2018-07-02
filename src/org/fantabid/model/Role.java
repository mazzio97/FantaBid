package org.fantabid.model;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ANY('-', "Any", 25),
    PORTIERE('P', "Portiere", 3),
    DIFENSORE('D', "Difensore", 8),
    CENTROCAMPISTA('C', "Centrocampista", 8),
    ATTACCANTE('A', "Attaccante", 6);
    
    private final char role;
    private final String toString;
    private final int maxInTeam;

    private Role(char role, String toString, int max) {
        this.role = role;
        this.toString = toString;
        this.maxInTeam = max;
    }

    public int getMaxInTeam() {
        return maxInTeam;
    }
    
    public char getRoleChar() {
        return role;
    }
    
    public String getRoleString() {
        return Optional.of(role).filter(c -> c != ANY.role).map(String::valueOf).orElse(null);
    }

    public static Role fromString(String roleString) {
        return Arrays.asList(Role.values())
                     .stream()
                     .filter(r -> !r.equals(ANY))
                     .filter(r -> r.getRoleString().equals(roleString))
                     .findFirst()
                     .orElse(ANY);
    }
    
    public String toString() {
        return toString;
    }
    
}
