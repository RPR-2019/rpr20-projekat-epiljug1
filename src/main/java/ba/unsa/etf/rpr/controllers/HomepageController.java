package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.enums.StageEnums;
import ba.unsa.etf.rpr.enums.Tooltips;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomepageController {
    @FXML
    public AnchorPane homepage;

    @FXML
    public Button btnYourProjects;

    @FXML
    public Button btnAllProjects;

    @FXML
    public Button btnAllDev;

    @FXML
    public Button btnAddProject;

    @FXML
    public Button editProfile;

    @FXML
    public TextField date;

    @FXML
    public Label user;


    private Developer developer;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private ProjectDAO projectDAO;
    private DeveloperDAO developerDAO;
    private int developerID;
    public HomepageController(Developer developer){
        projectDAO=ProjectDAO.getInstance();
        this.developer=developer;
        developerDAO = DeveloperDAO.getInstance();
        developerID = developerDAO.findIdOfDeveloper(developer.getUsername());
    }


    @FXML
    public void initialize(){
        user.setText(developer.getName()+ " "+developer.getSurname());
        date.setText(LocalDate.now().format(myFormatObj));


        btnYourProjects.setTooltip(new Tooltip(Tooltips.YOUR_PROJECTS.toString()));
        btnAllProjects.setTooltip(new Tooltip(Tooltips.OTHER_PROJECTS.toString()));
        btnAllDev.setTooltip(new Tooltip(Tooltips.ALL_DEVELOPERS.toString()));
        btnAddProject.setTooltip(new Tooltip(Tooltips.ADD_PROJECT.toString()));
        editProfile.setTooltip(new Tooltip(Tooltips.EDIT_PROFILE.toString()));

    }

    public void listAllDevelopers(ActionEvent actionEvent) {
        ListDevelopersController ctrl = new ListDevelopersController(developer);
        StageHandler.loadWindow(getClass().getResource("/fxml/listDevelopers.fxml"),StageEnums.ALL_DEVELOPERS,ctrl);
    }
    public void listAllProjects(ActionEvent actionEvent) {
        OtherProjectsController ctrl = new OtherProjectsController(developer);
        StageHandler.loadWindow(getClass().getResource("/fxml/otherProjects.fxml"),StageEnums.OTHER_PROJECTS,ctrl);

    }
    public void listAllYourProjects(ActionEvent actionEvent){
        UserProjectsController ctrl = new UserProjectsController(developer);
        StageHandler.loadWindow(getClass().getResource("/fxml/userProjects.fxml"), StageEnums.ALL_PROJECTS,ctrl);

    }
    @FXML
    public void logoutAction(ActionEvent actionEvent){
        Optional<ButtonType> result = AlertMaker.alertCONFIRMATION("",StageEnums.QUESTION_LOGOUT.toString());

        if (result.get() == ButtonType.OK){
            closeWindow();
            LoginController ctrl = new LoginController();
            StageHandler.loadWindow(getClass().getResource("/fxml/login.fxml"),StageEnums.LOGIN ,ctrl);
        }
    }

    @FXML
    public void addNewProjectAction(ActionEvent actionEvent) throws IOException {
        AddProjectController ctrl = new AddProjectController(developer);
        StageHandler.loadWindow(getClass().getResource("/fxml/addProject.fxml"),StageEnums.ADD_PROJECT,ctrl);
    }

    private void changeLang() throws IOException {
        Stage stage = (Stage) btnYourProjects.getScene().getWindow();
        stage.setTitle(StageEnums.LOGIN.toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"), ResourceBundle.getBundle("Translation"));
        loader.setController(this);
        stage.setTitle(StageEnums.HOME_PAGE.toString());
        stage.setScene(new Scene(loader.load()));
    }

    public void switchToBS(ActionEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("bs","BA"));
        changeLang();
    }

    public void switchToEN(ActionEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("en","US"));
        changeLang();
    }

    public void changeLangAction(MouseEvent mouseEvent) throws IOException {
        if(Locale.getDefault().getCountry().equals("US"))
            Locale.setDefault(new Locale("bs","BA"));
        else
            Locale.setDefault(new Locale("en","US"));
        changeLang();
    }

    public void aboutAction(ActionEvent actionEvent){
        BoxBlur blur = new BoxBlur(3, 3, 3);
        homepage.setEffect(blur);
        AboutAppController aboutAppController = new AboutAppController();
        Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/aboutApp.fxml"),StageEnums.ABOUT_APP,aboutAppController);
        stage.setResizable(false);
        stage.setOnHiding(event->{
            homepage.setEffect(null);
        });
    }

    public void editProfileAction(ActionEvent actionEvent){
        EditProfileController editProfileController = new EditProfileController(developer);
        Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/editProfile.fxml"),StageEnums.EDIT_PROFILE.toString(),editProfileController);
        stage.setResizable(false);
        stage.setOnHiding(event->{
            developer = developerDAO.findDeveloperByIDorUsername(developerID,"");
            user.setText(developer.getName()+ " "+developer.getSurname());
        });
    }

    public void closeWindow(){
        ((Stage) btnAddProject.getScene().getWindow()).close();
    }


}
