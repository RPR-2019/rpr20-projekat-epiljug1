package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.EmptyFld;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBugController {
    @FXML
    TextField nameFld;

    @FXML
    TextField typeFld;


    @FXML
    RadioButton high;

    @FXML
    RadioButton medium;

    @FXML
    RadioButton low;

    @FXML
    TextArea descFld;


    private ProjectDAO projectDAO;
    private BugDAO bugDAO;
    private Project project;


    public AddBugController(Project project){
        this.project=project;
        bugDAO = BugDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
    }

    @FXML
    public void initialize(){
        low.setSelected(true);

    }



    private String check(){
        if(high.isSelected()) return "High/Visoka";
        if(medium.isSelected()) return "Medium/Srednja";
        return "Low/Niska";
    }
    private boolean checkField(){
        if(nameFld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured", EmptyFld.NAME.toString()); return  false;}
        if(typeFld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured", EmptyFld.TYPE.toString()); return  false;}
        return true;
    }


    @FXML
    public void addBugAction(ActionEvent actionEvent){
        if(checkField()){
            bugDAO.addNewBug(new Bug(nameFld.getText(),descFld.getText(),typeFld.getText(),"New/Novi",project,check()));
            cancleAction(actionEvent);
        }
    }

    @FXML
    public void cancleAction(ActionEvent actionEvent){
        ((Stage)typeFld.getScene().getWindow()).close();
    }
}
