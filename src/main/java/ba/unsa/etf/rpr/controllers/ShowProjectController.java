package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.LoadWebPage;
import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.email.MailSender;
import ba.unsa.etf.rpr.enums.*;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShowProjectController {

    @FXML
    public StackPane stackPane;

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
    public TableColumn colAsgn;


    @FXML
    public TableView<Bug> tableViewSolvedBugs;

    @FXML
    public TableColumn colSolName;

    @FXML
    public TableColumn colSolType;

    @FXML
    public TableColumn colSolStatus;

    @FXML
    public TableColumn colSolDate;

    @FXML
    public TableColumn colSolCompl;

    @FXML
    public TableColumn<Bug,String> colSolSolver;

    @FXML
    public TableView<Bug> tableViewRequest;



    @FXML
    public TableColumn<Bug,String> colDev;

    @FXML
    public TableColumn colBug;

    @FXML
    public Label notificationLbl;

    @FXML
    public GridPane grid;

    @FXML
    public HBox hbox;

    @FXML
    public HBox hboxSend;

    @FXML
    public CheckBox approveChk;

    @FXML
    public CheckBox denyChk;

    @FXML
    public TextField usernameFld;

    @FXML
    public TextField emailFld;

    @FXML
    public TextArea textArea;

    @FXML
    public TableView<Developer> tableViewDevelopers;

    @FXML
    public TableColumn<Developer,String> colNameDev;


    @FXML
    public TableColumn colUsernameDev;

    @FXML
    public TableColumn colEmailDev;


    @FXML
    public TextField searchFld;

    @FXML
    public TableView<Developer> tableViewSearch;

    @FXML
    public TableColumn<Developer,String> colNameSearch;




    private final Project project;
    private final ProjectDAO projectDAO;
    private final BugDAO bugDAO;
    private final DeveloperDAO developerDAO;
    private ObservableList<Bug> listBugs;
    private ObservableList<Bug> listSolvedBugs;
    private ObservableList<Bug> listReguest;
    private ObservableList<Developer> listDevelopers;
    private ObservableList<Developer> listSearchDevelopers;
    private ObservableList<Developer> allDevelopers;
    private ObservableList<Bug> listAssignedBugs;

    private final int projectId;

    public ShowProjectController(Project project){
        this.project = project;

        developerDAO = DeveloperDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        bugDAO = BugDAO.getInstance();

        projectId = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(projectId));
        listSolvedBugs = FXCollections.observableArrayList(bugDAO.getAllSolvedBugsForProject(projectId));
        listReguest = FXCollections.observableArrayList(bugDAO.getBugReportsForProject(projectId));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(projectId));
        allDevelopers =  FXCollections.observableArrayList(developerDAO.getAllDevelopers(developerDAO.findIdOfDeveloper(project.getCreator().getUsername())));
        listSearchDevelopers = FXCollections.observableArrayList(allDevelopers);
        listAssignedBugs = FXCollections.observableArrayList(bugDAO.getAllAssignedBugs(projectId,0));
    }

    private void loadData() {
        int id = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(id));
        listReguest = FXCollections.observableArrayList(bugDAO.getBugReportsForProject(id));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(id));
    }
    private void refresh(boolean pieRefresh){
        listBugs.setAll(bugDAO.getAllBugsForProject(projectId));
        listSolvedBugs.setAll(bugDAO.getAllSolvedBugsForProject(projectId));
        listReguest.setAll(bugDAO.getBugReportsForProject(projectId));
        listAssignedBugs.setAll(bugDAO.getAllAssignedBugs(projectId,0));
        setNotification();
        listDevelopers.setAll(projectDAO.getAllDevelopersWhoWorksOnAProject(projectId));
        tableViewRequest.refresh();
        tableViewBugs.refresh();
        tableViewDevelopers.refresh();
        tableViewSolvedBugs.refresh();
        tableViewAssignedBugs.refresh();
        if(pieRefresh) pie.setData(projectDAO.getProjectGraphStatistic(project));
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

        tableViewAssignedBugs.setItems(listAssignedBugs);
        colAsgnName.setCellValueFactory(new PropertyValueFactory("bug_name"));
        colAsgnType.setCellValueFactory(new PropertyValueFactory("bug_type"));
        colAsgnStatus.setCellValueFactory(new PropertyValueFactory("status"));
        colAsgnDate.setCellValueFactory(new PropertyValueFactory("date_created"));
        colAsgnCompl.setCellValueFactory(new PropertyValueFactory("complexity"));
        colAsgn.setCellValueFactory(new PropertyValueFactory("assigned"));

        tableViewSolvedBugs.setItems(listSolvedBugs);
        colSolName.setCellValueFactory(new PropertyValueFactory("bug_name"));
        colSolType.setCellValueFactory(new PropertyValueFactory("bug_type"));
        colSolStatus.setCellValueFactory(new PropertyValueFactory("status"));
        colSolDate.setCellValueFactory(new PropertyValueFactory("date_created"));
        colSolCompl.setCellValueFactory(new PropertyValueFactory("complexity"));
        colSolSolver.setCellValueFactory(data -> new SimpleStringProperty(developerDAO.findDeveloperByIDorUsername(data.getValue().getSolver_id(),"").getUsername()));




        tableViewRequest.setItems(listReguest);
        colDev.setCellValueFactory(data -> new SimpleStringProperty(developerDAO.findDeveloperByIDorUsername(data.getValue().getRequest_id(),"").toString()));
        colBug.setCellValueFactory(new PropertyValueFactory("bug_name"));

        tableViewDevelopers.setItems(listDevelopers);
        colNameDev.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString()));
        colUsernameDev.setCellValueFactory(new PropertyValueFactory("username"));
        colEmailDev.setCellValueFactory(new PropertyValueFactory("email"));

        setNotification();

        tableViewSearch.setItems(FXCollections.observableList(listSearchDevelopers));
        colNameSearch.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString()));

        searchFld.textProperty().addListener((observableValue, oldValue, newValue) ->{
            if(!newValue.getBytes().toString().trim().isEmpty()){
                listSearchDevelopers.setAll(allDevelopers.stream().filter(developer1 -> {
                    return  developer1.getName().toLowerCase().contains(newValue.toLowerCase()) || developer1.getSurname().toLowerCase().contains(newValue.toLowerCase()) ||developer1.getUsername().toLowerCase().contains(newValue.toLowerCase()) || developer1.getEmail().toLowerCase().contains(newValue.toLowerCase());
                }).collect(Collectors.toCollection(ArrayList::new)));
                tableViewSearch.refresh();
            }else{
                listDevelopers.clear();
                tableViewSearch.refresh();
            }
        });

        tableViewBugs.setPlaceholder(new Label(Placeholders.BUGS.toString()));
        tableViewAssignedBugs.setPlaceholder(new Label(Placeholders.BUGS.toString()));
        tableViewRequest.setPlaceholder(new Label(Placeholders.REQUSETS.toString()));
        tableViewSolvedBugs.setPlaceholder(new Label(Placeholders.BUGS.toString()));
        tableViewSearch.setPlaceholder(new Label(Placeholders.DEVELOPERS.toString()));
        tableViewDevelopers.setPlaceholder(new Label(Placeholders.DEVELOPERS.toString()));

    }
    private void setNotification(){
        notificationLbl.setText( Validation.REQUESTS.toString() + listReguest.size()  );
    }

    @FXML
    public void approveAction(ActionEvent actionEvent){

        if(approveChk.isSelected()){
            reset();
            if(tableViewRequest.getSelectionModel().getSelectedItem()==null){
                AlertMaker.alertERROR("Error occured", BugInfo.SELECT_REQUEST.toString());
                approveChk.setSelected(false);
            }else{
                setData();
                System.out.println("APROVE");
                grid.setDisable(false);
                hboxSend.setDisable(false);
                denyChk.setSelected(false);
            }
        }
    }
    @FXML
    public void denyAction(ActionEvent actionEvent){
        if(denyChk.isSelected()){
            if(tableViewRequest.getSelectionModel().getSelectedItem()==null){
                AlertMaker.alertERROR("Error occured",BugInfo.SELECT_REQUEST.toString());
                denyChk.setSelected(false);
            }else {
                setData();
                System.out.println("DNY");
                grid.setDisable(false);
                hboxSend.setDisable(false);
                approveChk.setSelected(false);
            }
        }
    }

    @FXML
    public void confirmAction(ActionEvent actionEvent){
        Bug bug = tableViewRequest.getSelectionModel().getSelectedItem();
        if(bug==null){
            AlertMaker.alertERROR("Error occured","You need to select the item!");
            return;
        }
        if( approveChk.isSelected()) {
            approveChk.setSelected(false);
            bugDAO.approveRequestForSolving(bug.getRequest_id(), bug.getBug_name(), projectDAO.findID(bug.getProject()));
            refresh(true);
        }
        else if(denyChk.isSelected()){
            denyChk.setSelected(false);
            bugDAO.denyRequestForSolving(bug.getBug_name(), projectDAO.findID(bug.getProject()));
            refresh(false);
        }
    }

    @FXML
    public void sendMailAction(ActionEvent actionEvent){
        if(textArea.getText().trim().isEmpty()) AlertMaker.alertERROR("Error occured","Text area is empty!");
        else{
            MailLoginController mailLoginController = new MailLoginController(project.getCreator());
            Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/mailLogin.fxml"),"Mail info",mailLoginController);
            stage.setOnHiding( event -> {
                try {
                    MailSender.sendEmail(mailLoginController.getEmail(),mailLoginController.getPassword(),emailFld.getText(),"Your request is denied",textArea.getText());
                } catch (MessagingException e) {
                    AlertMaker.alertERROR("Error occured!", Validation.SENDING_EMAIL.toString());
                }
                textArea.setText("");
            });
        }
    }




    @FXML
    public void addBtnAction(ActionEvent actionEvent){
            if(tableViewSearch.getSelectionModel().getSelectedItem()!=null){
                String username = tableViewSearch.getSelectionModel().getSelectedItem().getUsername();

                if(listDevelopers.stream().anyMatch(dev ->{ return  dev.getUsername().equals(username);})){

                    AlertMaker.alertINFORMATION("INFORMATION","Developer \""+username+"\""+StageEnums.ALREADY_WORKING.toString());
                }else{
                    projectDAO.addDeveloperOnProject(projectId,developerDAO.findIdOfDeveloper(username));
                    AlertMaker.showMaterialDialog(stackPane,StageEnums.SUCCESFULLY_ADDED.toString(),"\""+username+"\""+StageEnums.SUCCESFULLY_ADDED_DEV.toString());
                    refresh(false);
                }

            }else AlertMaker.alertERROR("Error occured", EmptyFld.SELECT_DEV.toString());;
    }


    @FXML
    public void removeDeveloperAction(ActionEvent actionEvent){
        if(tableViewDevelopers.getSelectionModel().getSelectedItem()!=null){
            String username = tableViewDevelopers.getSelectionModel().getSelectedItem().getUsername();

            bugDAO.removeBugsForRemovedDeveloper(projectId,developerDAO.findIdOfDeveloper(username));
            projectDAO.removeDeveloperFromProject(projectId,developerDAO.findIdOfDeveloper(username));

            AlertMaker.showMaterialDialog(stackPane,StageEnums.SUCCESFULLY_REMOVED.toString(),"\""+username+"\""+StageEnums.SUCCESFULLY_REMOVED_DEV.toString());
            refresh(true);
        } else AlertMaker.alertERROR("Error occured",EmptyFld.SELECT_DEV.toString());
    }

    @FXML
    public void addBugAction(ActionEvent actionEvent){
        AddBugController addBugController = new AddBugController(project,false);
        Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/addBug.fxml"), StageEnums.ADD_BUG,addBugController);
        stage.setOnHiding( event -> {
            refresh(true);
        });
    }

    @FXML
    public void editBugAction(ActionEvent actionEvent){
        if(checkTable(tableViewBugs)) {
            EditBugController editBugController = new EditBugController(project, tableViewBugs.getSelectionModel().getSelectedItem());
            Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/editBug.fxml"), StageEnums.EDIT_BUG, editBugController);
            stage.setOnHiding(event -> {
                refresh(true);
            });
        }
    }

    @FXML
    public void addAssignedBugAction(ActionEvent actionEvent){
        System.out.println("ASSIGNED");
        AddBugController addBugController = new AddBugController(project,true);
        Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/addBug.fxml"),StageEnums.ADD_BUG,addBugController);
        stage.setOnHiding( event -> {
            refresh(true);
        });
    }

    @FXML
    public void editAssignedBugAction(ActionEvent actionEvent){
        if(checkTable(tableViewAssignedBugs)) {
            EditAssignedBugController editAssignedBugController = new EditAssignedBugController(project, tableViewAssignedBugs.getSelectionModel().getSelectedItem());
            Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/editAssignedBug.fxml"), StageEnums.EDIT_BUG, editAssignedBugController);
            stage.setOnHiding(event -> {
                refresh(true);
            });
        }
    }

    private boolean checkTable(TableView<Bug>table){
        if(table.getSelectionModel().getSelectedItem()==null){ AlertMaker.alertERROR("Error occured", BugInfo.SELECT.toString()); return false;}
        else return true;
    }

    @FXML
    public void openBugAction(ActionEvent actionEvent){
        if(checkTable(tableViewBugs)){
            OpenBugController ctrl = new OpenBugController(tableViewBugs.getSelectionModel().getSelectedItem());
            StageHandler.loadWindow(getClass().getResource("/fxml/openBug.fxml"),BugInfo.INFO.toString(),ctrl);
        }
    }

    @FXML
    public void openBugActionAssigned(ActionEvent actionEvent){
        if(checkTable(tableViewAssignedBugs)){
            OpenBugController ctrl = new OpenBugController(tableViewAssignedBugs.getSelectionModel().getSelectedItem());
            StageHandler.loadWindow(getClass().getResource("/fxml/openBug.fxml"),BugInfo.INFO.toString(),ctrl);
        }
    }


    @FXML
    public void closeAction(ActionEvent actionEvent){
        ((Stage)usernameFld.getScene().getWindow()).close();
    }


    @FXML
    public void sourceCodeLinkAction(ActionEvent actionEvent){
        LoadWebPage.loadWebpage(project.getCode_link());
    }


    private void reset(){
        usernameFld.setText("");
        emailFld.setText("");
    }

    private void setData(){
        Bug bug = tableViewRequest.getSelectionModel().getSelectedItem();
        Developer developer = developerDAO.findDeveloperByIDorUsername(bug.getRequest_id(),"");
        usernameFld.setText(developer.getUsername());
        emailFld.setText(developer.getEmail());
    }
}
