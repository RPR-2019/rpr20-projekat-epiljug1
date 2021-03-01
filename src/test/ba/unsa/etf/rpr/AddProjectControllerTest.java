//package ba.unsa.etf.rpr.rpr.rpr.controllers;
//
//import ba.unsa.etf.rpr.rpr.rpr.StageHandler;
//import ba.unsa.etf.rpr.rpr.rpr.database.BugDAO;
//import ba.unsa.etf.rpr.rpr.rpr.database.DeveloperDAO;
//import ba.unsa.etf.rpr.rpr.rpr.database.ProjectDAO;
//import ba.unsa.etf.rpr.rpr.rpr.enums.StageEnums;
//import ba.unsa.etf.rpr.rpr.rpr.model.Developer;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonBar;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.DialogPane;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(ApplicationExtension.class)
//class AddProjectControllerTest {
//    ProjectDAO projectDAO = ProjectDAO.getInstance();
//    DeveloperDAO developerDAO =DeveloperDAO.getInstance();
//    BugDAO bugDAO =BugDAO.getInstance();
//    Developer developer = developerDAO.findDeveloperByIDorUsername(1,"");
//    Stage stage2;
//    @BeforeEach
//    void def() throws SQLException {
//        developerDAO.backToDefaultDatabase();
//    }
//
//
//
//    @Start
//    public void start(Stage stage){
//
//        AddProjectController ctrl = new AddProjectController(developer);
//        stage = StageHandler.loadWindow(getClass().getResource("/fxml/addProject.fxml"), StageEnums.ADD_PROJECT,ctrl);
//        stage.toFront();
//        stage2=stage;
//
//    }
//
//    @Test
//    void addProject(FxRobot robot){
//        robot.clickOn("#nameFld").write("NEW PROJECT");
//        robot.clickOn("#clientNameFld").write("CLIENT NAME");
//        robot.clickOn("#clientEmailFld").write("CLIENT EMAIL");
//        robot.clickOn("#sourceCodeFld").write("SOURCE CODE");
//        robot.clickOn("#descriptionFld").write("THIS IS DESCRIPTION");
//        robot.clickOn("#addButton");
//        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
//        assertNotNull(dialogPane.lookupAll("New project has been successfuly added!"));
//
//        // Klik na dugme Ok
////       Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
////        System.out.println(dialogPane.getButtonTypes().get(0).getText());
//        //Button button = (Button) dialogPane.getButtonTypes().get(0);
////        robot.clickOn(okButton);
//        ((Stage)  dialogPane.getScene().getWindow()).close();
//
//        assertEquals(2,projectDAO.getAllProjects().size());
//        stage2.close();
//    }
//
//}