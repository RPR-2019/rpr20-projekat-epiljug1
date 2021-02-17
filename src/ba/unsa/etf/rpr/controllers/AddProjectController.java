package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.database.ProjectDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AddProjectController {
    @FXML
    TextField nameFld;

    @FXML
    TextArea descriptionFld;

    @FXML
    TextField clientNameFld;

    @FXML
    TextField clientEmailFld;

    @FXML
    AnchorPane anchorPane;

    @FXML
    TextField sourceCodeFld;

    private ProjectDAO projectDAO;
    private Developer developer;

    public AddProjectController(Developer developer){
        projectDAO = ProjectDAO.getInstance();
        this.developer = developer;
    }

    public boolean check(String field, String nameField){
        if(field.trim().isEmpty()) {
            AlertMaker.alertERROR("Error occured", nameField + " field is empty");
            return  false;
        }
        return true;
    }

    public void addAction(ActionEvent actionEvent){
        if(check(nameFld.getText(),"Name") && check(descriptionFld.getText(),"Description") && check(clientNameFld.getText(),"Client name field")
                && check(clientEmailFld.getText(),"Client e-mail") && check(sourceCodeFld.getText(),"Source code")){
            projectDAO.addNewProject(new Project(nameFld.getText(),descriptionFld.getText(),developer,clientNameFld.getText(),clientEmailFld.getText(), sourceCodeFld.getText()));
            //AlertMaker.alertINFORMATION("Information","Project has been successfully added!");
            AlertMaker.showMaterialDialog(anchorPane,"New project added",nameFld.getText()+" has been added!");
        //    closeWindowAction(actionEvent);
        }
    }

    public void closeWindowAction(ActionEvent actionEvent){
        Stage stage = (Stage) nameFld.getScene().getWindow();
        stage.close();
    }
}
