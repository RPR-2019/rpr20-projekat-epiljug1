package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controllers.LoginController;
import ba.unsa.etf.rpr.enums.StageEnums;
import javafx.application.Application;
import javafx.stage.Stage;

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

