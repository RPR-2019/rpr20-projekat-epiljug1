package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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

    public void openProjectAction(javafx.event.ActionEvent actionEvent) {
        System.out.println("OPEN");
        Project project = tableViewProjects.getSelectionModel().getSelectedItem();
        ShowProjectController ctrl = new ShowProjectController(project);
        Stage main = (Stage) tableViewProjects.getScene().getWindow();
        main.close();
        Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/showProject.fxml"),project.getName(),ctrl);
        stage.setOnHiding( event -> {
            main.show();
        });
    }

    public void editProjectAction(ActionEvent actionEvent) throws IOException {
        closeWindow(actionEvent);
        EditProjectController ctrl = new EditProjectController(tableViewProjects.getSelectionModel().getSelectedItem());

        Stage editProjectStage = StageHandler.loadWindow(getClass().getResource("/fxml/editProject.fxml"),"Edit project",ctrl );
        editProjectStage.setOnHiding( event -> {
                Project newProject = ctrl.getProject();
                if (newProject != null) {
                    try {
                        System.out.println("refresh");
                        listProjects.setAll(projectDAO.getAllProjectsOfDeveloper(developer));
                        tableViewProjects.refresh();
                        ((Stage)tableViewProjects.getScene().getWindow()).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } );
    }



    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }


    public void closeWindow(javafx.event.ActionEvent actionEvent){
        Stage stage = (Stage) tableViewProjects.getScene().getWindow();
        stage.close();
    }
}
