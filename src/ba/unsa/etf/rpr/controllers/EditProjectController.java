package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.database.ProjectDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditProjectController {
    @FXML
    TextField nameFld;

    @FXML
    TextArea descriptionFld;

    @FXML
    TextField clientNameFld;

    @FXML
    TextField clientEmailFld;

    @FXML
    TextField sourceCodeFld;

    @FXML
    AnchorPane anchorPane;

    private Project project;
    private ProjectDAO projectDAO;
    public EditProjectController(Project project){
        this.project=project;
        projectDAO = ProjectDAO.getInstance();
    }

    @FXML
    public void initialize(){
        nameFld.setText(project.getName());
        descriptionFld.setText(project.getDescription());
        clientNameFld.setText(project.getClient_name());
        clientEmailFld.setText(project.getClient_email());
    }

    @FXML
    public void closeWindowAction(ActionEvent actionEvent){
        Stage stage = (Stage) nameFld.getScene().getWindow();
        stage.close();
    }
    public boolean check(){
        if(nameFld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured","Name field is empty!"); return false;}
        if(descriptionFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured","Description field is empty!");  return false;}
        if(clientNameFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured","Client name field is empty!");  return false;}
        if(clientEmailFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured","Client e-mail field is empty!");  return false;}
        if(sourceCodeFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured","Source code field is empty!");  return false;}

        return  true;

    }

    @FXML
    public void saveAction(ActionEvent actionEvent){
        System.out.println("SAVE ACTION");
        int id = projectDAO.findID(project);
        if(check()){
            projectDAO.updateProject(id,nameFld.getText(),descriptionFld.getText(),clientNameFld.getText(),clientEmailFld.getText(),sourceCodeFld.getText());
            AlertMaker.showMaterialDialog(anchorPane,"Changes saved","Project has been successfully edited!");
        }
    }

    public Project getProject(){
        return project;
    }


}
