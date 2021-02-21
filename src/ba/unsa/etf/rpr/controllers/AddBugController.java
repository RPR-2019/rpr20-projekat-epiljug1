package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.Status;
import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddBugController {
    @FXML
    TextField nameFld;

    @FXML
    TextField typeFld;

    @FXML
    ChoiceBox<String> statusChoice;

    @FXML
    TextField complFld;

    private ProjectDAO projectDAO;
    private BugDAO bugDAO;
    private Project project;


    private ObservableList<String> listOfStatus;

    public AddBugController(Project project){
        listOfStatus = FXCollections.observableArrayList(Status.allStatus());

        this.project=project;
        bugDAO = BugDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
    }

    @FXML
    public void initialize(){
            statusChoice.setItems(listOfStatus);

    }
    private boolean check(String field, String nameField){
        if(field.trim().isEmpty()) {
            AlertMaker.alertERROR("Error occured", nameField + " field is empty");
            return  false;
        }
        return true;
    }
    private boolean checkChoice(){
        if(statusChoice.getSelectionModel().getSelectedItem()==null) {
            AlertMaker.alertERROR("Error occured", "You did not select any status");
            return  false;
        }
        return true;
    }
    @FXML
    public void addBugAction(ActionEvent actionEvent){
        if(check(nameFld.getText(),"Name") && check(typeFld.getText(),"Type")&& checkChoice() && check(complFld.getText(),"Complexity")){
            bugDAO.addNewBug(new Bug(nameFld.getText(),typeFld.getText(),statusChoice.getSelectionModel().getSelectedItem(),project,complFld.getText()));
            cancleAction(actionEvent);
        }
    }

    @FXML
    public void cancleAction(ActionEvent actionEvent){
        ((Stage)typeFld.getScene().getWindow()).close();
    }
}
