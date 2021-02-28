package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controllers.LoginController;
import ba.unsa.etf.rpr.enums.StageEnums;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ResourceBundle;


import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public  class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        LoginController ctrl = new LoginController();
        StageHandler.loadWindow(getClass().getResource("/fxml/login.fxml"), StageEnums.LOGIN.toString(),ctrl);
    }
    public static void main(String[] args){
        launch(args);
    }



}

