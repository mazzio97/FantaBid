package org.fantabid.model;

public enum LeagueType {
    CLASSIC("Classic League", false), BID("Bid League", true);
    
    private final String toString;
    private final boolean bid;

    private LeagueType(String toString, boolean bid) {
        this.toString = toString;
        this.bid = bid;
    }
    
    public boolean isBid() {
        return bid;
    }
    
    public String toString() {
        return toString;
    }
}
