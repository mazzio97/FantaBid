package org.fantabid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.fantabid.entities.Role;

public class Main {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb?user=mazzio&password=crostopiada";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        Arrays.asList(Role.values()).stream().map(Role::name).forEach(role -> {
            ResultSet r;
            try {
                r = s.executeQuery("SELECT * FROM " + role);
                System.out.println(role + ":");
                while (r.next()) {
                    System.out.println(r.getString(1) + " " + r.getString(2) + " " + r.getString(3) + " " + r.getString(4));
                }
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        s.close();
    }

}
