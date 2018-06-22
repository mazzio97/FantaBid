package org.fantabid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/worldb";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT * FROM country");
        while (r.next()) {
            System.out.println(r.getString(2));
        }
    }

}
