package utils;


import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class Utils {
    public static void showAlertMessage(String title, String message) {

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setHeaderText(message);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    public static String returnSymbol(String ch) {
        if (ch.equals("\n") || ch.equals("\t") || ch.equals("\b") || ch.equals("\r") || ch.hashCode() == 127 || ch.hashCode() == 0) {
            return "";
        }
        return ch;
    }
}
