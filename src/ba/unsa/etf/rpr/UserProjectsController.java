package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserProjectsController {
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

    private ObservableList<Project> listProjects;
    private ProjectDAO projectDAO;
    private Developer developer;

    public UserProjectsController(Developer developer){
        this.developer=developer;
        projectDAO = ProjectDAO.getInstance();
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

    public void editProjectAction(ActionEvent actionEvent) {
        System.out.println("EDIT");
    }
}