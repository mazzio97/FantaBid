package org.fantabid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.fantabid.entities.Player;
import org.fantabid.entities.Role;
import org.fantabid.stats.PlayersStats;

public class Main {

    public static void main(String[] args) throws SQLException {
        final PlayersStats stats = new PlayersStats("./players.xls");
//        final List<Player> goalkeepers = stats.getPlayerByRole(Role.PORTIERE);
//        final List<Player> defenders = stats.getPlayerByRole(Role.DIFENSORE);
//        final List<Player> midfielders = stats.getPlayerByRole(Role.CENTROCAMPISTA);
//        final List<Player> forwards = stats.getPlayerByRole(Role.ATTACCANTE);

        String url = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb?user=mazzio&password=crostopiada";
        Connection conn = DriverManager.getConnection(url);
        
        stats.getAllPlayers().forEach(p -> addPlayerToDB(conn, p));

        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT * FROM " + Arrays.asList(Role.values()).stream().map(Role::name).collect(Collectors.joining(", ", "", "")));
        while (r.next()) {
            System.out.println(r.getString(1) + " " + r.getString(2) + " " + r.getString(3) + " " + r.getString(4));
        }
        s.close();
    }
    
    private static void addPlayerToDB(final Connection conn, final Player p) {
        String table = p.getRole().name();
        PreparedStatement st;
        try {
            st = conn.prepareStatement("INSERT INTO " + table + " VALUES (?, ?, ?, ?)");
            st.setInt(1, p.getId());
            st.setString(2, p.getName());
            st.setString(3, p.getTeam());
            st.setInt(4, p.getPrice());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
