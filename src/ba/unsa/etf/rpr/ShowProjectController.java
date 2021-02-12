package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.HashMap;

public class ShowProjectController {
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
    public TableView<Bug> tableViewRequest;

    @FXML
    public TableColumn<Bug,String> colDev;

    @FXML
    public TableColumn colBug;

    @FXML
    public Label notificationLbl;

    private Project project;
    private ProjectDAO projectDAO;
    private BugDAO bugDAO;
    private DeveloperDAO developerDAO;
    private ObservableList<Bug> listBugs;
    private ObservableList<Bug> listReguest;
    private HashMap<Integer,Bug> mapBugsRequests ;


    public ShowProjectController(Project project){
        this.project = project;

        developerDAO = DeveloperDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        bugDAO = BugDAO.getInstance();

        int id = projectDAO.findID(project);
        mapBugsRequests = bugDAO.getBugReportsForProject(id);

        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(id));
        listReguest = FXCollections.observableArrayList(mapBugsRequests.values());
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

        tableViewRequest.setItems(listReguest);
        colDev.setCellValueFactory(data -> new SimpleStringProperty(developerDAO.findDeveloperByIDorUsername(data.getValue().getSolver_id(),"").toString()));
        colBug.setCellValueFactory(new PropertyValueFactory("bug_name"));

        if(mapBugsRequests.size()!=0)notificationLbl.setText("You have "+mapBugsRequests.size()+" notifications");
    }

}
