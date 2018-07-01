package org.fantabid.model;

public enum LeagueType {
    CLASSIC("Classic League"), BID("Bid League");
    
    private final String toString;

    private LeagueType(String toString) {
        this.toString = toString;
    }
    
    public String toString() {
        return toString;
    }
}
