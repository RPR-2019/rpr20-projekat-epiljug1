package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController {
    @FXML
    Button btnYourProjects;
    @FXML
    Button btnAllProjects;
    @FXML
    Button btnAllDev;
    @FXML
    Button btnLogOut;
    @FXML
    Button btnAddProject;
    @FXML
    Button btnSettings;

    public HomepageController(){

    }

    @FXML
    public void initialize(){

    }

    public void listAllYourProjects(ActionEvent actionEvent){
        System.out.println("LIST");
    }


}
