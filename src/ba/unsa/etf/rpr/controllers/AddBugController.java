package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.EmptyFld;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddBugController {
    @FXML
    public TextField nameFld;

    @FXML
    public TextField typeFld;


    @FXML
    public RadioButton high;

    @FXML
    public RadioButton medium;

    @FXML
    public RadioButton low;

    @FXML
    public TextArea descFld;

    @FXML
    public ChoiceBox<Developer> choiceAssign;

    @FXML
    public Label assignLbl;


    private ProjectDAO projectDAO;
    private BugDAO bugDAO;
    private DeveloperDAO developerDAO;
    private Project project;
    private ObservableList<Developer> listDevelopers;
    private boolean isForAssign;


    public AddBugController(Project project,boolean isForAssign){
        this.project=project;
        bugDAO = BugDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        developerDAO = DeveloperDAO.getInstance();
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(projectDAO.findID(project)));
        this.isForAssign=isForAssign;
    }


    @FXML
    public void initialize(){
        low.setSelected(true);
        choiceAssign.setItems(listDevelopers);
        if(!isForAssign) {
            choiceAssign.setVisible(false);
            assignLbl.setVisible(false);
        }
    }





    private String check(){
        if(high.isSelected()) return "High/Visoka";
        if(medium.isSelected()) return "Medium/Srednja";
        return "Low/Niska";
    }
    private boolean checkField(){
        if(nameFld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured", EmptyFld.NAME.toString()); return  false;}
        if(typeFld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured", EmptyFld.TYPE.toString()); return  false;}
        if(isForAssign && choiceAssign.getSelectionModel().getSelectedItem()==null) {AlertMaker.alertERROR("Error occured",EmptyFld.SELECT_DEV.toString()); return false;}
        return true;
    }


    @FXML
    public void addBugAction(ActionEvent actionEvent){
        if(checkField()){
            Bug newBug = new Bug(nameFld.getText(),descFld.getText(),typeFld.getText(),"New/Novi",project,check());
            if(isForAssign) newBug.setStatus("Assigned/Dodijeljen");
            bugDAO.addNewBug(newBug);
            if(isForAssign){
                System.out.println("DODAVANJE U TABLU ASSIGN");
                bugDAO.addAssign(projectDAO.findID(project),bugDAO.findId(newBug),developerDAO.findIdOfDeveloper(choiceAssign.getSelectionModel().getSelectedItem().getUsername()));
            }

            cancleAction(actionEvent);
        }
    }

    @FXML
    public void cancleAction(ActionEvent actionEvent){
        ((Stage)typeFld.getScene().getWindow()).close();
    }
}
