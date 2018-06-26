package org.fantabid.stats;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.fantabid.entities.Player;
import org.fantabid.entities.Role;

public class PlayersStats {
    public static final String PLAYERS_EXCEL_FILE_PATH = "./players.xls";
    private final List<Player> players = new LinkedList<>();

    public PlayersStats(final String path) {
        Optional<Workbook> workbook = Optional.empty();
        try {
            workbook = Optional.of(WorkbookFactory.create(new File(path)));
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.map(w -> w.getSheetAt(0)).get();
        DataFormatter dataFormatter = new DataFormatter();
        sheet.forEach(row -> {
            players.add(new Player(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(0))),
                                   dataFormatter.formatCellValue(row.getCell(2)),
                                   dataFormatter.formatCellValue(row.getCell(1)),
                                   dataFormatter.formatCellValue(row.getCell(3)),
                                   Integer.parseInt(dataFormatter.formatCellValue(row.getCell(4)))));
        });
        workbook.ifPresent(w -> {
            try {
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Player> getAllPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<Player> getPlayerByRole(final Role role) {
        return players.stream().filter(p -> p.getRole().equals(role)).collect(Collectors.toList());
    }

}
