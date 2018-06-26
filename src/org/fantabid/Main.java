package org.fantabid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.fantabid.stats.PlayersStats;

public class Main {

    public static void main(String[] args) throws SQLException {
        final PlayersStats stats = new PlayersStats("./players.xls");
        String url = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb?user=mazzio&password=crostopiada";
        Connection conn = DriverManager.getConnection(url);
        
        stats.getAllPlayers().forEach(p -> {
            PreparedStatement st;
            try {
                st = conn.prepareStatement("INSERT INTO CALCIATORE VALUES (?, ?, ?, ?, ?)");
                st.setInt(1, p.getId());
                st.setString(2, p.getName());
                st.setString(3, p.getTeam());
                st.setInt(4, p.getPrice());
                st.setString(5, p.getRole().name().substring(0, 1));
                st.executeUpdate();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT * FROM CALCIATORE");
        while (r.next()) {
            System.out.println(r.getString(1) + " " + r.getString(2) + " " + r.getString(3) + " " + r.getString(4) + " " + r.getString(5));
        }
        s.close();
    }

}
