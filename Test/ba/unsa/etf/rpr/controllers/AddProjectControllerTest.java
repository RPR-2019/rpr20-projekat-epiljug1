package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.StageEnums;
import ba.unsa.etf.rpr.model.Developer;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class AddProjectControllerTest {
    ProjectDAO projectDAO = ProjectDAO.getInstance();
    DeveloperDAO developerDAO =DeveloperDAO.getInstance();
    BugDAO bugDAO =BugDAO.getInstance();
    Developer developer = developerDAO.findDeveloperByIDorUsername(1,"");
    @BeforeEach
    void def() throws SQLException {
        developerDAO.backToDefaultDatabase();
    }
    @Start
    public void start(Stage stage){
        AddProjectController ctrl = new AddProjectController(developer);
        stage = StageHandler.loadWindow(getClass().getResource("/fxml/addProject.fxml"), StageEnums.ADD_PROJECT,ctrl);
        stage.toFront();
    }

    @Test
    void addProject(FxRobot robot){
        robot.clickOn("#nameFld").write("NEW PROJECT");
        robot.clickOn("#clientNameFld").write("CLIENT NAME");
        robot.clickOn("#clientEmailFld").write("CLIENT EMAIL");
        robot.clickOn("#sourceCodeFld").write("SOURCE CODE");
        robot.clickOn("#descriptionFld").write("THIS IS DESCRIPTION");
        robot.clickOn("#addButton");
        assertEquals(2,projectDAO.getAllProjects().size());
    }

}