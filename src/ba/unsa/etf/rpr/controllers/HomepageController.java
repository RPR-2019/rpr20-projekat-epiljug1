package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import ba.unsa.etf.rpr.database.ProjectDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class HomepageController {
    @FXML
    Button btnYourProjects;
    @FXML
    Button btnAllProjects;
    @FXML
    Button btnAllDev;
    @FXML
    Button btnLogOut;
    @FXML
    Button btnAddProject;
    @FXML
    Button btnSettings;
    @FXML
    TextField date;
    @FXML
    Label user;
    private Developer developer;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    private ProjectDAO projectDAO;
    public HomepageController(Developer developer){
        projectDAO=ProjectDAO.getInstance();
        this.developer=developer;
    }


    @FXML
    public void initialize(){
        user.setText(developer.getName()+ " "+developer.getSurname());
        date.setText(LocalDate.now().format(myFormatObj));
    }

    public void listAllDevelopers(ActionEvent actionEvent) throws IOException {
        Stage allProjects = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/listDevelopers.fxml"));
        ListDevelopersController ctrl = new ListDevelopersController(developer);
        loader.setController(ctrl);
        Parent root = loader.load();
        allProjects.setTitle("All developers");
        allProjects.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        allProjects.show();
    }
    public void listAllProjects(ActionEvent actionEvent) throws IOException {
        Stage allProjects = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/otherProjects.fxml"));
        OtherProjectsController ctrl = new OtherProjectsController(developer);
        loader.setController(ctrl);
        Parent root = loader.load();
        allProjects.setTitle("Other projects");
        allProjects.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        allProjects.show();
    }
    public void listAllYourProjects(ActionEvent actionEvent) throws IOException {

        Stage allProjects = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userProjects.fxml"));
        UserProjectsController ctrl = new UserProjectsController(developer);
        loader.setController(ctrl);
        Parent root = loader.load();
        allProjects.setTitle("All projects");
        allProjects.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        allProjects.show();
    }
    @FXML
    public void logoutAction(ActionEvent actionEvent) throws IOException {

        Optional<ButtonType> result = AlertMaker.alertCONFIRMATION("","Do you want to log out?");

        if (result.get() == ButtonType.OK){
            closeWindow();
            Stage signUpStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            LoginController ctrl = new LoginController();
            loader.setController(ctrl);
            Parent root = loader.load();
            signUpStage.setTitle("Login");
            signUpStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            signUpStage.show();
        } else {

        }

    }

    @FXML
    public void addNewProjectAction(ActionEvent actionEvent) throws IOException {
        Stage allProjects = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProject.fxml"));
        AddProjectController ctrl = new AddProjectController(developer);
        loader.setController(ctrl);
        Parent root = loader.load();
        allProjects.setTitle("Add project");
        allProjects.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        allProjects.show();
    }
    @FXML
    public void proba(ActionEvent actionEvent){
        Project novi = new Project("naziv","desc",developer,"client","mejl");
        projectDAO.addNewProject(novi);
    }

    public void closeWindow(){
        Stage stage = (Stage) btnAddProject.getScene().getWindow();
        stage.close();
    }


}
