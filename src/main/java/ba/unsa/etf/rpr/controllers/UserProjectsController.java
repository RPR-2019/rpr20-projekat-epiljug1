package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.Validation;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.reports.ReportsHandler;
import ba.unsa.etf.rpr.StageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public class UserProjectsController {
    @FXML
    public TableView<Project> tableViewProjects;

    @FXML
    public TableColumn colName;

    @FXML
    public TableColumn colDate;

    @FXML
    public TableColumn colClient;

    @FXML
    public TableColumn colClientEmail;

    private ObservableList<Project> listProjects;
    private ProjectDAO projectDAO;
    private DeveloperDAO developerDAO;
    private Developer developer;

    public UserProjectsController(Developer developer){
        this.developer=developer;
        projectDAO = ProjectDAO.getInstance();
        developerDAO = DeveloperDAO.getInstance();
        listProjects = FXCollections.observableArrayList(projectDAO.getAllProjectsOfDeveloper(developer));
    }

    @FXML
    public void initialize(){
        tableViewProjects.setItems(listProjects);
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colDate.setCellValueFactory(new PropertyValueFactory("dateProjectCreated"));
        colClient.setCellValueFactory(new PropertyValueFactory("client_name"));
        colClientEmail.setCellValueFactory(new PropertyValueFactory("client_email"));
    }


    public boolean check(TableView tableView){
        if(tableView.getSelectionModel().getSelectedItem()!=null) return true;
        AlertMaker.alertERROR("Error occured", Validation.SELECT_PROJECT.toString());
        return false;
    }

    public void openProjectAction(javafx.event.ActionEvent actionEvent) throws IOException {
        if(check(tableViewProjects)) {
            System.out.println("OPEN");
            Project project = tableViewProjects.getSelectionModel().getSelectedItem();

            Stage main = (Stage) tableViewProjects.getScene().getWindow();
            main.close();

            ShowProjectController ctrl = new ShowProjectController(project);
            Stage signUpStage = StageHandler.loadWindow(getClass().getResource("/fxml/showProject.fxml"),"Show project",ctrl);
            signUpStage.setOnHiding(event -> {
                main.show();
            });
        }
    }

    public void editProjectAction(ActionEvent actionEvent) throws IOException {
        if(check(tableViewProjects)) {
            closeWindow(actionEvent);
            EditProjectController ctrl = new EditProjectController(tableViewProjects.getSelectionModel().getSelectedItem());

            Stage editProjectStage = StageHandler.loadWindow(getClass().getResource("/fxml/editProject.fxml"), "Edit project", ctrl);
            editProjectStage.setOnHiding(event -> {
                Project newProject = ctrl.getProject();
                if (newProject != null) {
                    try {
                        System.out.println("refresh");
                        listProjects.setAll(projectDAO.getAllProjectsOfDeveloper(developer));
                        tableViewProjects.refresh();
                        ((Stage) tableViewProjects.getScene().getWindow()).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    public void exportAction(ActionEvent actionEvent){
        try {
            new ReportsHandler().showReport(projectDAO.getConn(), developerDAO.findIdOfDeveloper(developer.getUsername()), developer);
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
    public void closeWindow(javafx.event.ActionEvent actionEvent){ ((Stage) tableViewProjects.getScene().getWindow()).close(); }
}
