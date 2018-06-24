package org.fantabid;

import java.sql.Statement;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.fantabid.entities.Player;

public class Main {

    public static final String PLAYERS_EXCEL_FILE_PATH = "./players.xls";
    
    public static void main(String[] args) throws IOException, InvalidFormatException, SQLException {
        final List<Player> players = new LinkedList<>();
        Workbook workbook = WorkbookFactory.create(new File(PLAYERS_EXCEL_FILE_PATH));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        sheet.forEach(row -> {
            players.add(new Player(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(0))),
                                   dataFormatter.formatCellValue(row.getCell(2)),
                                   dataFormatter.formatCellValue(row.getCell(1)),
                                   dataFormatter.formatCellValue(row.getCell(3)),
                                   Integer.parseInt(dataFormatter.formatCellValue(row.getCell(4)))));
        });
        
        workbook.close();

        String url = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb?user=mazzio&password=crostopiada";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        Player toAdd = players.get(0);
//        players.forEach(p -> System.out.println(p.getName() + "->" + p.getTeam()));
        s.executeUpdate("INSERT INTO CALCIATORE VALUES (" + toAdd.getId() + ", " + toAdd.getName() + ", " + toAdd.getTeam() + ", " + toAdd.getPrice() + ", " + toAdd.getRole() + ");");
        ResultSet r = s.executeQuery("SELECT * FROM player");
        while (r.next()) {
            System.out.println(r.getString(2) + " " + r.getString(3) + " " + r.getString(4) + " " + r.getString(5));
        }
        System.out.println(players.size());
    }
}
