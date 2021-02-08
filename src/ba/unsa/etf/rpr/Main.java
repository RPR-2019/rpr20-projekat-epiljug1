package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;


import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public  class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        LoginController ctrl = new LoginController();
        loader.setController(ctrl);
//        HomepageController ctrl = new HomepageController();
//        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Sing in");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }
    public static void main(String[] args) throws SQLException {
        launch(args);
//        DeveloperDAO developerDAO =  DeveloperDAO.getInstance();
//        developerDAO.backToDefaultDatabase();
//
//        ArrayList<Developer> dev= developerDAO.getAllDevelopers();
//        for(Developer d : dev){
//            System.out.println(d.getName() + " " + d.getSurname() + " " + d.getUsername());
//        }
//
//        Developer novi2 =  new Developer("test","test","test","test","test");
//        developerDAO.addDeveloper(novi2);
//        System.out.println("==============================================");
//        ArrayList<Developer> dev2= developerDAO.getAllDevelopers();
//        for(Developer d : dev2) {
//            System.out.println(d.getName() + " " + d.getSurname() + " " + d.getUsername());
//        }
//        Project novi = new Project("nazivProjekta","opisProjekta",novi2);
//        novi.setDateProjectCreated( LocalDate.of(2000,10,30)) ;
//        System.out.println(novi.getDateProjectCreated());
    }

}

