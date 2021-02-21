package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.Status;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditBugController {
    @FXML
    TextField nameFld;

    @FXML
    TextField typeFld;

    @FXML
    ChoiceBox<String> statusChoice;

    @FXML
    TextField complFld;

    @FXML
    Label solverLbl;

    @FXML
    ChoiceBox<Developer> solverChoice;

    private ProjectDAO projectDAO;
    private BugDAO bugDAO;

    private Project project;
    private Bug bug;

    private ObservableList<String> listOfStatus;
    private ObservableList<Developer> listOfDevelopers;

    public EditBugController(Project project,Bug bug){
        bugDAO = BugDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        this.project=project;
        this.bug = bug;
        listOfStatus = FXCollections.observableArrayList(Status.allStatus());
        listOfDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(projectDAO.findID(project)));
    }

    @FXML
    public void initialize(){
        if(bug.getStatus()!="fixed") {
            solverLbl.setDisable(true);
            solverChoice.setDisable(true);
        }
        nameFld.setText(bug.getBug_name());
        typeFld.setText(bug.getBug_type());
        statusChoice.setValue(bug.getStatus());
        complFld.setText(bug.getComplexity());

        statusChoice.setItems(listOfStatus);
        solverChoice.setItems(listOfDevelopers);

        statusChoice.setOnAction(event -> {
            if(statusChoice.getValue()=="fixed"){
                solverLbl.setDisable(false);
                solverChoice.setDisable(false);
            }else{
                solverLbl.setDisable(true);
                solverChoice.setDisable(true);
            }
        });
    }

    @FXML
    public void saveChangesAction(ActionEvent actionEvent){

    }

    @FXML
    public void cancleAction(javafx.event.ActionEvent actionEvent){
        ((Stage)typeFld.getScene().getWindow()).close();
    }
}
