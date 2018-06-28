package org.fantabid.entities;

public class LeagueRule {

    private final String name;
    private String description;
    
    public LeagueRule(final String name) {
        this.name = name;
    }
    
    public LeagueRule(final String name, final String description) {
        this(name);
        setDescription(description);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
