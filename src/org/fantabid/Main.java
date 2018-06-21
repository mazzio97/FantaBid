package org.fantabid;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.fantabid.entities.Player;

public class Main {

    public static final String PLAYERS_EXCEL_FILE_PATH = "./players.xls";
    
    public static void main(String[] args) throws IOException, InvalidFormatException {
        final Set<Player> players = new HashSet<>();
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
        
        players.forEach(p -> System.out.println(p.getName() + "->" + p.getTeam()));
        System.out.println(players.size());
    }
}
