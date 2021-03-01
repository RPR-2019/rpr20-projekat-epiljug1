package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.enums.BugInfo;
import ba.unsa.etf.rpr.model.Bug;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class OpenBugController {
    @FXML
    public TextField nameFld;

    @FXML
    public TextField typeFld;

    @FXML
    public TextField complFld;

    @FXML
    public TextField assignFld;

    @FXML
    public TextArea descFld;

    private Bug bug;

    public OpenBugController(Bug bug){
        this.bug = bug;
    }

    @FXML
    public void initialize(){
        nameFld.setText(bug.getBug_name());
        typeFld.setText(bug.getBug_type());
        complFld.setText(bug.getComplexity());
        if(bug.getAssigned()==null)
            assignFld.setText(BugInfo.ALL_DEVELOPERS.toString());
        else
            assignFld.setText( bug.getAssigned().getUsername() );
        descFld.setText(bug.getBug_desc());
    }

    @FXML
    public void cancleAction(ActionEvent actionEvent){
        ((Stage) nameFld.getScene().getWindow()).close();
    }

}
