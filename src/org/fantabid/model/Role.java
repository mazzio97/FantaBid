package org.fantabid.model;

import java.util.Optional;

public enum Role {
    ANY('-', "Any"),
    PORTIERE('P', "Portiere"),
    DIFENSORE('D', "Difensore"),
    CENTROCAMPISTA('C', "Centrocampista"),
    ATTACCANTE('A', "Attaccante");
    
    private final char role;
    private final String toString;

    private Role(char role, String toString) {
        this.role = role;
        this.toString = toString;
    }
    
    public char getRoleChar() {
        return role;
    }
    
    public String getRoleString() {
        return Optional.of(role).filter(c -> c != ANY.role).map(String::valueOf).orElse(null);
    }
    
    public String toString() {
        return toString;
    }
}
