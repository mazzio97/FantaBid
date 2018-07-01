package org.fantabid.view;

import org.fantabid.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public final class Views {
    
    private static final String PACKAGE_PATH = "/org/fantabid/layout/";
    private static final String EXTENSION = ".fxml";
    
    private Views() { }
    
    public static Scene loadLoginScene() {
        return loadPrimaryStage("Login");
    }
    
    public static Scene loadSignupScene() {
        return loadPrimaryStage("Signup");
    }
    
    public static Scene loadUserAreaScene() {
        return loadPrimaryStage("UserArea");
    }
    
    public static Scene loadTeamScene() {
        return loadPrimaryStage("Team");
    }
    
    public static Scene loadLeaguesScene() {
        return loadPrimaryStage("Leagues");
    }
    
    public static Scene loadNewLeagueScene() {
        return loadPrimaryStage("NewLeague");
    }
    
    public static Scene loadLeagueSignupScene() {
        return loadPrimaryStage("LeagueSignup");
    }
    
    public static Scene loadBetInfoScene() {
        return loadPrimaryStage("BetInfo");
    }
    
    private static Scene loadPrimaryStage(String fileName) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Views.class.getResource(PACKAGE_PATH + fileName + EXTENSION)));
            Main.getPrimaryStage().setScene(scene);
        } catch (Exception e) {
            Dialogs.showErrorDialog("Internal Error Occurred", "The application will stop.");
            e.printStackTrace();
            System.exit(1);
        }
        return scene;
    }
}
