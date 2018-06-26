package org.fantabid.entities;

public class Player {

    private final int id;
    private final String name;
    private Role role;
    private String team;
    private int price;
    
    public Player(final int id, final String name, final String role, final String team, final int price) {
        this.id = id;
        this.name = name;
        switch (role) {
        case "P":            
            this.role = Role.PORTIERE;
            break;

        case "D":            
            this.role = Role.DIFENSORE;
            break;
            
        case "C":            
            this.role = Role.CENTROCAMPISTA;
            break;
            
        case "A":            
            this.role = Role.ATTACCANTE;
            break;

        default:
            break;
        }
        this.team = team;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getTeam() {
        return team;
    }

    public int getPrice() {
        return price;
    }

}
