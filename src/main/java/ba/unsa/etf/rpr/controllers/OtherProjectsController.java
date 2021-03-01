package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.Placeholders;
import ba.unsa.etf.rpr.enums.Validation;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.reports.ReportsHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public class OtherProjectsController {
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

    @FXML
    public TableColumn<Project,String> colCreator;

    private ObservableList<Project> listProjects;
    private ProjectDAO projectDAO;
    private DeveloperDAO developerDAO;
    private Developer developer;

    public OtherProjectsController(Developer developer){
        this.developer=developer;
        developerDAO = DeveloperDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        listProjects = FXCollections.observableArrayList(projectDAO.getAllProjectsForDeveloper(developer));
    }

    private Stage getStage(){
        return (Stage) tableViewProjects.getScene().getWindow();
    }

    @FXML
    public void initialize(){
        tableViewProjects.setItems(listProjects);
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colDate.setCellValueFactory(new PropertyValueFactory("dateProjectCreated"));
        colCreator.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCreator().toString()));
        colClient.setCellValueFactory(new PropertyValueFactory("client_name"));
        colClientEmail.setCellValueFactory(new PropertyValueFactory("client_email"));

        tableViewProjects.setPlaceholder(new Label(Placeholders.PROJECTS.toString()));
    }


    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }


    public void closeWindow(javafx.event.ActionEvent actionEvent){
        Stage stage = (Stage) tableViewProjects.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void exportAction(ActionEvent actionEvent){
        try {
            new ReportsHandler().showReport(projectDAO.getConn(),developerDAO.findIdOfDeveloper(developer.getUsername()),developer.getName()+" "+developer.getSurname() + "("+developer.getUsername()+")");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void openProjectAction(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("OPEN");
        if(tableViewProjects.getSelectionModel().getSelectedItem()!=null){
            getStage().close();
            ShowOtherProjectController ctrl = new ShowOtherProjectController(tableViewProjects.getSelectionModel().getSelectedItem(),developer);
            Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/showOtherProject.fxml"),"Project info",ctrl);
            stage.setOnHiding( event -> {
               getStage().show();
            });

        }else AlertMaker.alertERROR("Error occured", Validation.SELECT_PROJECT.toString());
    }


}
