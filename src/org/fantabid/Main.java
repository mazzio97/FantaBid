package org.fantabid;

import org.fantabid.view.Views;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class Main extends Application {

    private static Stage primaryStage;

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(final Stage primaryStage) {
        setPrimaryStage(primaryStage);
        primaryStage.setTitle("Fantabid");
        primaryStage.getIcons().add(new Image("org/fantabid/images/Icon.png"));
        Views.loadUserAreaScene();
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
