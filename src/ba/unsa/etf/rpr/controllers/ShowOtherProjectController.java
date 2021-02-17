package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.LoadWebPage;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ShowOtherProjectController {

    @FXML
    PieChart pie;

    @FXML
    TextField nameFld;

    @FXML
    TextField creatorFld;

    @FXML
    TextField clientFld;

    @FXML
    TextField clientEmailFld;

    @FXML
    TextField dateFld;

    @FXML
    TextArea descFld;

    @FXML
    TableView<Bug> tableViewBugs;

    @FXML
    public TableColumn colName;

    @FXML
    public TableColumn colType;

    @FXML
    public TableColumn colStatus;

    @FXML
    public TableColumn colDate;

    @FXML
    public TableColumn colCompl;

    @FXML
    TableView<Developer> tableViewDevelopers;

    @FXML
    public TableColumn<Developer,String> colNameDev;

    @FXML
    public TableColumn colUsernameDev;

    @FXML
    public TableColumn colEmailDev;

    public ShowOtherProjectController(){

    }
    private Project project;
    private ProjectDAO projectDAO;
    private BugDAO bugDAO;
    private DeveloperDAO developerDAO;
    private ObservableList<Bug> listBugs;
    private ObservableList<Developer> listDevelopers;

    private  int projectId;

    public ShowOtherProjectController(Project project){
        this.project = project;

        developerDAO = DeveloperDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        bugDAO = BugDAO.getInstance();

        projectId = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(projectId));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(projectId));
    }

    @FXML
    public void initialize(){
        pie.setData(projectDAO.getProjectGraphStatistic(project));
        nameFld.setText(project.getName());
        creatorFld.setText(project.getCreator().toString());
        clientFld.setText(project.getClient_name());
        clientEmailFld.setText(project.getClient_email());
        dateFld.setText(project.getDateProjectCreated());
        descFld.setText(project.getDescription());

        tableViewBugs.setItems(listBugs);
        colName.setCellValueFactory(new PropertyValueFactory("bug_name"));
        colType.setCellValueFactory(new PropertyValueFactory("bug_type"));
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));
        colDate.setCellValueFactory(new PropertyValueFactory("date_created"));
        colCompl.setCellValueFactory(new PropertyValueFactory("complexity"));

        tableViewDevelopers.setItems(listDevelopers);
        colNameDev.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString()));
        colUsernameDev.setCellValueFactory(new PropertyValueFactory("username"));
        colEmailDev.setCellValueFactory(new PropertyValueFactory("email"));

    }

    @FXML
    public void sourceCodeLinkAction(ActionEvent actionEvent){
        LoadWebPage.loadWebpage(project.getCode_link());
    }


    @FXML
    public void openBugAction(ActionEvent actionEvent){
        System.out.println("OPEN BUG ACTION");
    }

    @FXML
    public void closeAction(ActionEvent actionEvent){
        ((Stage)tableViewBugs.getScene().getWindow()).close();
    }
}





























