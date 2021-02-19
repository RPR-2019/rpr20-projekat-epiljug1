package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.*;
import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.email.MailSender;
import ba.unsa.etf.rpr.model.Bug;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
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
    StackPane stackPane;

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
    public TableColumn<Bug,String> colSolver;

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
    TableView<Developer> tableViewDevelopers;
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
    private ObservableList<Bug> listReguest;
    private ObservableList<Developer> listDevelopers;
    private ObservableList<Developer> listSearchDevelopers;
    private ObservableList<Developer> allDevelopers;

    private final int projectId;

    public ShowProjectController(Project project){
        this.project = project;

        developerDAO = DeveloperDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        bugDAO = BugDAO.getInstance();

        projectId = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(projectId));
        listReguest = FXCollections.observableArrayList(bugDAO.getBugReportsForProject(projectId));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(projectId));
        listSearchDevelopers = FXCollections.observableArrayList(new ArrayList<Developer>());
        allDevelopers =  FXCollections.observableArrayList(developerDAO.getAllDevelopers());
    }

    private void loadData() {
        int id = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(id));
        listReguest = FXCollections.observableArrayList(bugDAO.getBugReportsForProject(id));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(id));
    }
    private void refresh(){
        listBugs.setAll(bugDAO.getAllBugsForProject(projectId));
        listReguest.setAll(bugDAO.getBugReportsForProject(projectId));
        setNotification();
        listDevelopers.setAll(projectDAO.getAllDevelopersWhoWorksOnAProject(projectId));
        tableViewRequest.refresh();
        tableViewBugs.refresh();
        tableViewDevelopers.refresh();
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
        colSolver.setCellValueFactory(data -> new SimpleStringProperty(developerDAO.findDeveloperByIDorUsername(data.getValue().getSolver_id(),"").getUsername()));

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


    }
    private void setNotification(){
        if (listReguest.size() != 0) {
            notificationLbl.setText("You have " + listReguest.size() + " requests");
        } else {
            notificationLbl.setText("");
        }
    }

    @FXML
    public void approveAction(ActionEvent actionEvent){

        if(approveChk.isSelected()){
            reset();
            if(tableViewRequest.getSelectionModel().getSelectedItem()==null){
                AlertMaker.alertERROR("Error occured","You need to select the item!");
                approveChk.setSelected(false);
            }else{
                System.out.println("APROVE");
                grid.setDisable(true);
                hboxSend.setDisable(true);
                denyChk.setSelected(false);
            }
        }
    }
    @FXML
    public void denyAction(ActionEvent actionEvent){
        if(denyChk.isSelected()){
            if(tableViewRequest.getSelectionModel().getSelectedItem()==null){
                AlertMaker.alertERROR("Error occured","You need to select the item!");
                denyChk.setSelected(false);
            }else {
                setData();

                System.out.println("DNY");
                approveChk.setSelected(false);
                grid.setDisable(false);
                grid.setVisible(true);
                hboxSend.setDisable(false);
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
            refresh();
            pie.setData(projectDAO.getProjectGraphStatistic(project));
        }
        else if(denyChk.isSelected()){
            denyChk.setSelected(false);
            bugDAO.denyRequestForSolving(bug.getBug_name(), projectDAO.findID(bug.getProject()));
            refresh();
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
                    AlertMaker.alertERROR("Error occured!","Something went wrong while sending! Please check your info");
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
                    AlertMaker.alertINFORMATION("INFORMATION","Developer \""+username+"\" already working on this project!");
                }else{
                    projectDAO.addDeveloperOnProject(projectId,developerDAO.findIdOfDeveloper(username));
                    AlertMaker.showMaterialDialog(stackPane,"Successfuly added","\""+username+"\" has been succesfuly added to this project!");
                    refresh();
                }

            }else AlertMaker.alertERROR("Error occured","You did not select any developer!");
    }


    @FXML
    public void removeDeveloperAction(ActionEvent actionEvent){
        if(tableViewDevelopers.getSelectionModel().getSelectedItem()!=null){
            String username = tableViewDevelopers.getSelectionModel().getSelectedItem().getUsername();
            projectDAO.removeDeveloperFromProject(projectId,developerDAO.findIdOfDeveloper(username));
            AlertMaker.showMaterialDialog(stackPane,"Successfuly removed","\""+username+"\" has been succesfuly removed from this project!");
            refresh();
        } else AlertMaker.alertERROR("Error occured","You did not select any developer!");
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
