package org.fantabid;

import static org.fantabid.generated.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.fantabid.view.Views;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class Main extends Application {

    private final static String DB_URL = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb";
    private final static String USER = "mazzio";
    private final static String PASSWORD = "crostopiada";
    private static Stage primaryStage;
    public static DSLContext create;
    
    public static void main(final String[] args) throws SQLException {
        System.getProperties().setProperty("org.jooq.no-logo", "true");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        create = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> r = create.select()
                         .from(CALCIATORE)
                         .where(CALCIATORE.RUOLO.eq("A"))
                         .and(CALCIATORE.SQUADRA.eq("Inter"))
                         .fetch();
        
        r.forEach(rec -> System.out.println(rec.getValue(CALCIATORE.NOME) + " " + rec.getValue(CALCIATORE.SQUADRA) +  " " + rec.getValue(CALCIATORE.PREZZOSTANDARD)));
        
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

    /**
     * Static setter of the primary stage.
     * @param stage
     *          the stage to set as primary
     */
    public static void setPrimaryStage(final Stage stage) {
        Main.primaryStage = stage;
    }

    /**
     * Getter for primaryStage.
     * @return
     *      the primaryStage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}