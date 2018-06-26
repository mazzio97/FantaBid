package org.fantabid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.fantabid.view.Views;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class Main extends Application {

    private static Stage primaryStage;

    public static void main(final String[] args) throws SQLException {
        String url = "jdbc:postgresql://fantabid.czlxuwq0tdm7.us-west-2.rds.amazonaws.com/fantabidb?user=mazzio&password=crostopiada";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
//        s.executeUpdate("INSERT INTO products VALUES (1, 'Cheese', 9.99);");
        ResultSet r = s.executeQuery("SELECT * FROM Calciatore");
        while (r.next()) {
            System.out.println(r.getString(2));
        }
        
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