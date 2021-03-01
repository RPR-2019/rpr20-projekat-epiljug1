package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.LoadWebPage;
import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.enums.BugInfo;
import ba.unsa.etf.rpr.enums.Placeholders;
import ba.unsa.etf.rpr.enums.StageEnums;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

public class ShowOtherProjectController {

    @FXML
    public PieChart pie;

    @FXML
    public TextField nameFld;

    @FXML
    public TextField creatorFld;

    @FXML
    public TextField clientFld;

    @FXML
    public TextField clientEmailFld;

    @FXML
    public TextField dateFld;

    @FXML
    public TextArea descFld;

    @FXML
    public TableView<Bug> tableViewBugs;

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
    public TableView<Bug> tableViewAssignedBugs;

    @FXML
    public TableColumn colAsgnName;

    @FXML
    public TableColumn colAsgnType;

    @FXML
    public TableColumn colAsgnStatus;

    @FXML
    public TableColumn colAsgnDate;

    @FXML
    public TableColumn colAsgnCompl;

    @FXML
    public TableView<Developer> tableViewDevelopers;

    @FXML
    public TableColumn<Developer,String> colNameDev;

    @FXML
    public TableColumn colUsernameDev;

    @FXML
    public TableColumn colEmailDev;

    @FXML
    public Button requestBtn;

    @FXML
    public Button requestAssignedBtn;

    private Project project;
    private Developer developer;

    private ProjectDAO projectDAO;
    private BugDAO bugDAO;
    private DeveloperDAO developerDAO;

    private ObservableList<Bug> listBugs;
    private ObservableList<Developer> listDevelopers;
    private ObservableList<Bug> listAssignedBugs;

    private  int projectId;

    public ShowOtherProjectController(Project project,Developer developer){
        this.project = project;
        this.developer = developer;

        developerDAO = DeveloperDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        bugDAO = BugDAO.getInstance();

        projectId = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(projectId));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(projectId));
        listAssignedBugs = FXCollections.observableArrayList(bugDAO.getAllAssignedBugs(projectId,developerDAO.findIdOfDeveloper(developer.getUsername())));
    }
    private void refresh(){
        listBugs.setAll(bugDAO.getAllBugsForProject(projectId));
        listDevelopers.setAll(projectDAO.getAllDevelopersWhoWorksOnAProject(projectId));
        listAssignedBugs.setAll(bugDAO.getAllAssignedBugs(projectId,developerDAO.findIdOfDeveloper(developer.getUsername())));
        tableViewBugs.refresh();
        tableViewDevelopers.refresh();
        tableViewAssignedBugs.refresh();
    }
    @FXML
    public void initialize(){

        requestBtn.setTooltip(new Tooltip(BugInfo.SEND_REQUEST.toString()));
        requestAssignedBtn.setTooltip(new Tooltip(BugInfo.SEND_REQUEST.toString()));
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


        tableViewAssignedBugs.setItems(listAssignedBugs);
        colAsgnName.setCellValueFactory(new PropertyValueFactory("bug_name"));
        colAsgnType.setCellValueFactory(new PropertyValueFactory("bug_type"));
        colAsgnStatus.setCellValueFactory(new PropertyValueFactory("status"));
        colAsgnDate.setCellValueFactory(new PropertyValueFactory("date_created"));
        colAsgnCompl.setCellValueFactory(new PropertyValueFactory("complexity"));

        tableViewDevelopers.setItems(listDevelopers);
        colNameDev.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString()));
        colUsernameDev.setCellValueFactory(new PropertyValueFactory("username"));
        colEmailDev.setCellValueFactory(new PropertyValueFactory("email"));

        tableViewBugs.setPlaceholder(new Label(Placeholders.BUGS.toString()));
        tableViewDevelopers.setPlaceholder(new Label(Placeholders.DEVELOPERS.toString()));
        tableViewAssignedBugs.setPlaceholder(new Label(Placeholders.BUGS.toString()));

    }

    @FXML
    public void sourceCodeLinkAction(ActionEvent actionEvent){
        LoadWebPage.loadWebpage(project.getCode_link());
    }



    private Bug checkTable(TableView<Bug> table){
        if(table.getSelectionModel().getSelectedItem()==null){ AlertMaker.alertERROR("Error occured", BugInfo.SELECT.toString()); return null;}
        else return table.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void openBugAction(ActionEvent actionEvent){
        if(checkTable(tableViewBugs)!=null){
            OpenBugController ctrl = new OpenBugController(tableViewBugs.getSelectionModel().getSelectedItem());
            StageHandler.loadWindow(getClass().getResource("/fxml/openBug.fxml"),BugInfo.INFO.toString(),ctrl);
        }
    }

    @FXML
    public void openBugActionAssigned(ActionEvent actionEvent){
        if(checkTable(tableViewAssignedBugs)!=null){
            OpenBugController ctrl = new OpenBugController(tableViewAssignedBugs.getSelectionModel().getSelectedItem());
            StageHandler.loadWindow(getClass().getResource("/fxml/openBug.fxml"),BugInfo.INFO.toString(),ctrl);
        }
    }

    @FXML
    public void requestAction(ActionEvent actionEvent){
        if(tableViewBugs.getSelectionModel().getSelectedItem()!=null) {
            Optional<ButtonType> result = AlertMaker.alertCONFIRMATION(BugInfo.SEND_REQUEST_BUG.toString(), BugInfo.CONFIRM_SEND_REQUEST.toString());
            if (result.get() == ButtonType.OK) {
             //   bugDAO.addNewRequest(developerDAO.findIdOfDeveloper(developer.getUsername()), projectId, bugDAO.findId(tableViewBugs.getSelectionModel().getSelectedItem()));
                boolean isRequested = bugDAO.addRequestForBug(bugDAO.findId(tableViewBugs.getSelectionModel().getSelectedItem()),developerDAO.findIdOfDeveloper(developer.getUsername()) );
                if(!isRequested){
                    AlertMaker.alertINFORMATION("",BugInfo.ALREADY_REQUSTED.toString());
                    return;
                }
                tableViewBugs.getSelectionModel().getSelectedItem().setStatus("Pending/Čekanje");
                refresh();
            }
        }else AlertMaker.alertERROR("Error occured",BugInfo.SELECT.toString());
    }

    @FXML
    public void requestAssignedAction(ActionEvent actionEvent){
        if(tableViewAssignedBugs.getSelectionModel().getSelectedItem()!=null) {
            Optional<ButtonType> result = AlertMaker.alertCONFIRMATION(BugInfo.SEND_REQUEST_BUG.toString(), BugInfo.CONFIRM_SEND_REQUEST.toString());
            if (result.get() == ButtonType.OK) {
           //     bugDAO.addNewRequest(developerDAO.findIdOfDeveloper(developer.getUsername()), projectId, bugDAO.findId(tableViewAssignedBugs.getSelectionModel().getSelectedItem()));
                bugDAO.addRequestForBug(bugDAO.findId(tableViewAssignedBugs.getSelectionModel().getSelectedItem()),developerDAO.findIdOfDeveloper(developer.getUsername()) );
                tableViewAssignedBugs.getSelectionModel().getSelectedItem().setStatus("Pending/Čekanje");
                refresh();
            }
        }else AlertMaker.alertERROR("Error occured",BugInfo.SELECT.toString());
    }

    @FXML
    public void addBugAction(ActionEvent actionEvent){
        AddBugController addBugController = new AddBugController(project,false);
        Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/addBug.fxml"),StageEnums.ADD_BUG,addBugController);
        stage.setOnHiding( event -> {
            refresh();
        });
    }


    @FXML
    public void closeAction(ActionEvent actionEvent){
        ((Stage)tableViewBugs.getScene().getWindow()).close();
    }
}





























