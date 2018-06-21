package org.fantabid.entities;

public class Player {

    private final int id;
    private final String name;
    private Role role = null;
    private String team;
    private int price;
    
    public Player(final int id, final String name, final String role, final String team, final int price) {
        this.id = id;
        this.name = name;
        switch (role) {
        case "P":            
            this.role = Role.GOALKEEPER;
            break;

        case "D":            
            this.role = Role.DEFENDER;
            break;
            
        case "C":            
            this.role = Role.MIDFIELDER;
            break;
            
        case "A":            
            this.role = Role.FORWARD;
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
