package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.database.ProjectDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OtherProjectsController {
    @FXML
    TableView<Project> tableViewProjects;
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
    private Developer developer;

    public OtherProjectsController(Developer developer){
        this.developer=developer;
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
    }


    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }


    public void closeWindow(javafx.event.ActionEvent actionEvent){
        Stage stage = (Stage) tableViewProjects.getScene().getWindow();
        stage.close();
    }
    public void openProjectAction(javafx.event.ActionEvent actionEvent) {
        System.out.println("OPEN");
        if(tableViewProjects.getSelectionModel().getSelectedItem()!=null){
            getStage().close();
            ShowOtherProjectController ctrl = new ShowOtherProjectController(tableViewProjects.getSelectionModel().getSelectedItem());
            Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/showOtherProject.fxml"),"Project info",ctrl);
            stage.setOnHiding( event -> {
               getStage().show();
            });
        }else AlertMaker.alertERROR("Error occured","You did not select any project!");
    }


}
