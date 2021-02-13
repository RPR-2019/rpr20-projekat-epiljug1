package ba.unsa.etf.rpr;

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
import java.util.HashMap;
import java.util.Map;

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
    public TableColumn colNameSearch;




    private Project project;
    private ProjectDAO projectDAO;
    private BugDAO bugDAO;
    private DeveloperDAO developerDAO;
    private ObservableList<Bug> listBugs;
    private ObservableList<Bug> listReguest;
    private ObservableList<Developer> listDevelopers;

    int id ;

    public ShowProjectController(Project project){
        this.project = project;

        developerDAO = DeveloperDAO.getInstance();
        projectDAO = ProjectDAO.getInstance();
        bugDAO = BugDAO.getInstance();

        id = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(id));
        listReguest = FXCollections.observableArrayList(bugDAO.getBugReportsForProject(id));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(id));
    }

    private void loadData() {
        int id = projectDAO.findID(project);
        listBugs = FXCollections.observableArrayList(bugDAO.getAllBugsForProject(id));
        listReguest = FXCollections.observableArrayList(bugDAO.getBugReportsForProject(id));
        listDevelopers = FXCollections.observableArrayList(projectDAO.getAllDevelopersWhoWorksOnAProject(id));
    }
    private void refresh(){
        listBugs.setAll(bugDAO.getAllBugsForProject(id));
        listReguest.setAll(bugDAO.getBugReportsForProject(id));
        listDevelopers.setAll(projectDAO.getAllDevelopersWhoWorksOnAProject(id));
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

        tableViewRequest.setItems(listReguest);
        colDev.setCellValueFactory(data -> new SimpleStringProperty(developerDAO.findDeveloperByIDorUsername(data.getValue().getRequest_id(),"").toString()));
        colBug.setCellValueFactory(new PropertyValueFactory("bug_name"));

        tableViewDevelopers.setItems(listDevelopers);
        colNameDev.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().toString()));
        colUsernameDev.setCellValueFactory(new PropertyValueFactory("username"));
        colEmailDev.setCellValueFactory(new PropertyValueFactory("email"));

        if(listReguest.size()!=0)notificationLbl.setText("You have "+listReguest.size()+" notifications");


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
                    AlertMaker.alertINFORMATION("Successfuly sended","Your mail is successfuly sended");
                 } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    public void searchAction(ActionEvent actionEvent){

    }

    @FXML
    public void addBtnAction(ActionEvent actionEvent){

    }

    @FXML
    public void closeAction(ActionEvent actionEvent){
        ((Stage)usernameFld.getScene().getWindow()).close();
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
