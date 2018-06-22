package org.fantabid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb?user=mazzio&password=crostopiada";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
//        s.executeUpdate("INSERT INTO products VALUES (1, 'Cheese', 9.99);");
        ResultSet r = s.executeQuery("SELECT * FROM products");
        while (r.next()) {
            System.out.println(r.getString(2));
        }
    }

}
