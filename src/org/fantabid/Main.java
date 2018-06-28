package org.fantabid;

import static org.fantabid.generated.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.fantabid.entities.LeagueRule;
import org.fantabid.model.Queries;
import org.fantabid.view.Views;
import org.jooq.Result;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class Main extends Application {

    private static final String DB_URL = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb";
    private static final String USER = "mazzio";
    private static final String PASSWORD = "crostopiada";
    private static Stage primaryStage;
    private static Connection connection;
    
    public static void main(final String[] args) throws SQLException {
        System.getProperties().setProperty("org.jooq.no-logo", "true");
        connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//        Result<?> resultSet = Queries.query.select(CALCIATORE.NOME, CALCIATORE.PREZZOSTANDARD)
//                                           .from(CALCIATORE)
//                                           .where(CALCIATORE.RUOLO.eq("A"))
//                                           .and(CALCIATORE.SQUADRA.eq("Inter"))
//                                           .fetch();
//        resultSet.stream()
//                 .map(r -> r.intoList())
//                 .forEach(System.out::println);
//        
//        List<LeagueRule> rules = new LinkedList<>();
//        rules.add(new LeagueRule("Porta inviolata", 
//                                 "Conferisce un bonus +1 al portiere in caso non subisca gol nella partita"));
//        rules.add(new LeagueRule("Modificatore difesa", 
//                                 "Utilizzabile schierando almeno 4 difensori, conferisce un bonus in base alla media voto del "
//                                 + "portiere e dei 3 migliori difensori (>= 6 -> 1; >= 6.5 -> 3; >= 7 -> 6)"));
//        rules.add(new LeagueRule("Assist da fermo", 
//                                 "Conferisce al giocatore che ha effettuato un assist da calcio di punizione, rimessa laterale o calcio d'angolo un bonus di +0.5 invece che +1"));
//        rules.add(new LeagueRule("Gol su rigore", 
//                                 "Conferisce al giocatore che ha segnato su rigore un bonus di +2 invece che di +3"));
//        rules.add(new LeagueRule("Gol decisivo", 
//                                 "Conferisce un bonus +1 al giocatore che ha segnato il gol determinante per raggiungere il pareggio o la vittoria finale contro la squadra avversaria"));
//        rules.forEach(r -> {
//            Queries.query.insertInto(REGOLA)
//                         .values(rules.indexOf(r) + 1, r.getName(), r.getDescription())
//                         .execute();
//        });
        launch();
    }

    @Override
    public void start(final Stage primaryStage) {
        setPrimaryStage(primaryStage);
        primaryStage.setTitle("Fantabid");
        primaryStage.getIcons().add(new Image("org/fantabid/images/Icon.png"));
        Views.loadLoginScene();
        primaryStage.show();
        primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2);
    }

    public static void setPrimaryStage(final Stage stage) {
        Main.primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static Connection getConnection() {
        return connection;
    }
}