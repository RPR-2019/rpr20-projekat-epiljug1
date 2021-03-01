package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.EmptyFld;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Optional;


public class AddProjectController {
    @FXML
    public TextField nameFld;

    @FXML
    public TextArea descriptionFld;

    @FXML
    public TextField clientNameFld;

    @FXML
    public TextField clientEmailFld;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public TextField sourceCodeFld;

    private ProjectDAO projectDAO;
    private Developer developer;

    public AddProjectController(Developer developer){
        projectDAO = ProjectDAO.getInstance();
        this.developer = developer;
    }


    private boolean check(){
        if(nameFld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured", EmptyFld.NAME.toString()); return false;}
        if(sourceCodeFld.getText().trim().isEmpty()){AlertMaker.alertERROR("Error occured", EmptyFld.SOURCE_CODE.toString()); return false;}
        return  true;
    }

    public void addAction(ActionEvent actionEvent){
        if(check()){
            projectDAO.addNewProject(new Project(nameFld.getText(),descriptionFld.getText(),developer,clientNameFld.getText(),clientEmailFld.getText(), sourceCodeFld.getText()));
            Dialog dialog;
            if(Locale.getDefault().getCountry().equals("US"))
                dialog= AlertMaker.showMaterialDialog(anchorPane,"New project added",nameFld.getText()+" has been added!");
            else
                dialog= AlertMaker.showMaterialDialog(anchorPane,"Novi projekat je dodan",nameFld.getText()+" je uspješo dodan!");
            dialog.setOnHiding(dialogEvent -> {
                closeWindowAction(actionEvent);
            });
        }
    }

    private void reset(){
        nameFld.setText("");
        descriptionFld.setText("");
        clientEmailFld.setText("");
        clientNameFld.setText("");
        sourceCodeFld.setText("");
    }

    public void closeWindowAction(ActionEvent actionEvent){
        ((Stage) nameFld.getScene().getWindow()).close();
    }
}
