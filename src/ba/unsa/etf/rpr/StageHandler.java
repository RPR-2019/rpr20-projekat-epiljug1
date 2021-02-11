package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class StageHandler {


    public static Stage loadWindow(URL loc, String title,Object object ) {
        Stage parentStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            loader.setController(object);
            Parent root = loader.load();
            parentStage.setTitle(title);
            parentStage.setScene(new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
            parentStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parentStage;
    }

}