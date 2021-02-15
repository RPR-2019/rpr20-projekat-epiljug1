package ba.unsa.etf.rpr.controllers;

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
        Project project = tableViewProjects.getSelectionModel().getSelectedItem();
        if(project!=null) {
            System.out.println(project.getName());
            System.out.println(project.getDescription());
            System.out.println(project.getClient_name());
            System.out.println(project.getClient_email());
            System.out.println(project.getDateProjectCreated());
        }
    }


}
