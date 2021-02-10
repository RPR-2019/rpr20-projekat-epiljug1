package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ShowDeveloperController {
    @FXML
    TextField nameFld;
    @FXML
    TextField usernameFld;
    @FXML
    TextField emailFld;
    @FXML
    TextField surnameFld;

    private Developer developer;

    public ShowDeveloperController(Developer developer){
        this.developer=developer;
    }

    @FXML
    public void initialize(){
        nameFld.setText(developer.getName());
        surnameFld.setText(developer.getSurname());
        emailFld.setText(developer.getEmail());
        usernameFld.setText(developer.getUsername());
    }
}
