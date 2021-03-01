package ba.unsa.etf.rpr.alert;

import ba.unsa.etf.rpr.enums.StageEnums;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Locale;
import java.util.Optional;

public class AlertMaker {
    public static void alertERROR(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        if(Locale.getDefault().getCountry().equals("US"))
            alert.setHeaderText(title);
        else
            alert.setHeaderText("Došlo je do greške");
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }
    public static void alertINFORMATION(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(Locale.getDefault().getCountry().equals("US"))
            alert.setTitle("Message");
        else
            alert.setTitle("Poruka");
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }
    public static Optional<ButtonType> alertCONFIRMATION(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(StageEnums.CONFIRMATION.toString());
        alert.setHeaderText(title);
        alert.setContentText(content);styleAlert(alert);

        return  alert.showAndWait();
    }

    public static Dialog showMaterialDialog( Node nodeToBeBlurred, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        Dialog dialog = new Dialog();


        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        dialog.setHeaderText(header);
        dialog.setContentText(body);
        dialog.getDialogPane().getButtonTypes().add(type);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/css/dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        dialog.show();
        Toolkit.getDefaultToolkit().beep();

        dialog.setOnCloseRequest(dialogEvent -> {
            nodeToBeBlurred.setEffect(null);
           // Stage stage= (Stage) nodeToBeBlurred.getScene().getWindow();
           // stage.close();
        });

        nodeToBeBlurred.setEffect(blur);
        return dialog;
    }



    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/css/dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }
}
