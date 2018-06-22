package org.fantabid.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public final class Dialogs {
    
    public static Optional<ButtonType> showConfirmationDialog(String header, String message) {
        return showDialog(AlertType.CONFIRMATION, "Confirmation", header, message);
    }
    
    public static Optional<ButtonType> showWarningDialog(String header, String message) {
        return showDialog(AlertType.WARNING, "Warning", header, message);
    }
    
    public static Optional<ButtonType> showErrorDialog(String header, String message) {
        return showDialog(AlertType.ERROR, "Error", header, message);
    }
    
    public static Optional<ButtonType> showDialog(AlertType type, String title, String header, String message) {
        final Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
