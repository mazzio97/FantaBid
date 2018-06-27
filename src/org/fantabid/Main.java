package org.fantabid;

import static org.fantabid.generated.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.fantabid.utils.Queries;
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
        Result<?> resultSet = Queries.query.select(CALCIATORE.NOME, CALCIATORE.PREZZOSTANDARD)
                                           .from(CALCIATORE)
                                           .where(CALCIATORE.RUOLO.eq("A"))
                                           .and(CALCIATORE.SQUADRA.eq("Inter"))
                                           .fetch();

        resultSet.stream()
                 .map(r -> r.intoList())
                 .forEach(System.out::println);
        
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