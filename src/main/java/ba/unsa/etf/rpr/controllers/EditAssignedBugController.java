package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.EmptyFld;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditAssignedBugController {
    @FXML
    public TextField nameFld;

    @FXML
    public TextField typeFld;

    @FXML
    public TextArea descFld;

    @FXML
    public RadioButton high;

    @FXML
    public RadioButton medium;

    @FXML
    public RadioButton low;

    @FXML
    public RadioButton assignedRb;

    @FXML
    public RadioButton fixedRb;

    @FXML
    public TextField assignedFld;

    private Project project;
    private Bug bug;
    private DeveloperDAO developerDAO;
    private ProjectDAO projectDAO;
    private BugDAO bugDAO;


    public EditAssignedBugController(Project project, Bug bug) {
        bugDAO = BugDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        developerDAO = DeveloperDAO.getInstance();
        this.project = project;
        this.bug = bug;

    }

    @FXML
    public void initialize(){
        assignedRb.setSelected(true);
        nameFld.setText(bug.getBug_name());
        typeFld.setText(bug.getBug_type());
        descFld.setText(bug.getBug_desc());
        assignedFld.setText(bug.getAssigned().getName()+ " " + bug.getAssigned().getSurname());
        complexity();

    }
    private void complexity(){
        if(bug.getComplexity().contains("High")) high.setSelected(true);
        else
        if(bug.getComplexity().contains("Medium")) medium.setSelected(true);
        else
            low.setSelected(true);
    }

    private String checkStatus(){
        if(fixedRb.isSelected()) return "Fixed/Riješen";
        return "Assigned/Dodijeljen";
    }


    private String checkComplexity(){
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
    public void saveChangesAction(ActionEvent actionEvent){
        if(checkField()) {
            Bug newBug = new Bug(nameFld.getText(), descFld.getText(), typeFld.getText(), checkStatus(), project, checkComplexity());
            newBug.setDate_created(bug.getDate_created());

            if (fixedRb.isSelected()) {
                newBug.setRequest_id(0);
                newBug.setSolver_id(developerDAO.findIdOfDeveloper(bug.getAssigned().getUsername()));
            }

            bugDAO.editBug(newBug, bug.getBug_name(), projectDAO.findID(project));
            cancleAction(actionEvent);
        }
    }

    @FXML
    public void cancleAction(javafx.event.ActionEvent actionEvent){
        ((Stage)typeFld.getScene().getWindow()).close();
    }
}
