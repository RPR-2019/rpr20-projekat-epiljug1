package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ShowDeveloperController {
    @FXML
    TextField nameFld;
    @FXML
    TextField usernameFld;
    @FXML
    TextField emailFld;
    @FXML
    TextField surnameFld;
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
    TableView<Project> tableViewOtherProjects;
    @FXML
    public TableColumn colName2;
    @FXML
    public TableColumn colDate2;
    @FXML
    public TableColumn colClient2;
    @FXML
    public TableColumn colClientEmail2;
    @FXML
    public TableColumn<Project,String> colCreator;

    private ObservableList<Project> listProjects;
    private ObservableList<Project> listOtherProjects;
    private ProjectDAO projectDAO;
    private Developer developer;

    public ShowDeveloperController(Developer developer){
        projectDAO = ProjectDAO.getInstance();
        this.developer=developer;
        listProjects = FXCollections.observableArrayList(projectDAO.getAllProjectsOfDeveloper(developer));
        listOtherProjects = FXCollections.observableArrayList(projectDAO.getAllProjectsForDeveloper(developer));
    }

    @FXML
    public void initialize(){
        nameFld.setText(developer.getName());
        surnameFld.setText(developer.getSurname());
        emailFld.setText(developer.getEmail());
        usernameFld.setText(developer.getUsername());
        tableViewProjects.setItems(listProjects);
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colDate.setCellValueFactory(new PropertyValueFactory("dateProjectCreated"));
        colClient.setCellValueFactory(new PropertyValueFactory("client_name"));
        colClientEmail.setCellValueFactory(new PropertyValueFactory("client_email"));

        tableViewOtherProjects.setItems(listOtherProjects);
        colName2.setCellValueFactory(new PropertyValueFactory("name"));
        colDate2.setCellValueFactory(new PropertyValueFactory("dateProjectCreated"));
        colCreator.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCreator().toString()));
        colClient2.setCellValueFactory(new PropertyValueFactory("client_name"));
        colClientEmail2.setCellValueFactory(new PropertyValueFactory("client_email"));
    }

    @FXML
    public void closeWindowAction(ActionEvent actionEvent){
        Stage stage = (Stage) nameFld.getScene().getWindow();
        stage.close();
    }
}
