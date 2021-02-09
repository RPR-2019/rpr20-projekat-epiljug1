package ba.unsa.etf.rpr;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class AlertMaker {
    public static void alertERROR(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }
    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        LibraryAssistantUtil.setStageIcon(stage);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/css/dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }
}
