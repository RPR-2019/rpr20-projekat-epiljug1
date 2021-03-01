package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controllers.LoginController;
import ba.unsa.etf.rpr.database.BugDAO;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.StageEnums;
import javafx.scene.control.*;
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
class LoginControllerTest {
    ProjectDAO projectDAO = ProjectDAO.getInstance();
    DeveloperDAO developerDAO =DeveloperDAO.getInstance();
    BugDAO bugDAO =BugDAO.getInstance();


    public static boolean sadrziStil(TextField polje, String stil) {
        for (String s : polje.getStyleClass())
            if (s.equals(stil)) return true;
        return false;
    }

    private void reset(FxRobot robot){
        robot.clickOn("#usernamefld").write("");
        robot.clickOn("#passwordfld").write("");
    }
    @BeforeEach
    void def() throws SQLException {
        developerDAO.backToDefaultDatabase();
    }
    @Start
    public void start(Stage stage){
        LoginController ctrl = new LoginController();
        stage = StageHandler.loadWindow(getClass().getResource("/fxml/login.fxml"), StageEnums.LOGIN.toString(),ctrl);
        stage.toFront();
    }
//    @Test
//    void validation(FxRobot robot){
//        reset(robot);
//        TextField polje = robot.lookup("#usernamefld").queryAs(TextField.class);
//        assertTrue(sadrziStil(polje, "ok"));
//        assertEquals("-fx-border-color: red",polje.getStyle());
//
//        robot.clickOn("#usernamefld").write("Ime");
//        assertTrue(sadrziStil(polje, "ok"));
//        assertEquals("-fx-border-color: lightgreen",polje.getStyle());
//
//        PasswordField polje2 = robot.lookup("#passwordfld").queryAs(PasswordField.class);
//        assertTrue(sadrziStil(polje2, "ok"));
//        assertEquals("-fx-border-color: red",polje2.getStyle());
//
//        robot.clickOn("#passwordfld").write("password");
//        assertTrue(sadrziStil(polje, "ok"));
//        assertEquals("-fx-border-color: lightgreen",polje2.getStyle());
//    }
    @Test
    void validation2(FxRobot robot){
        reset(robot);
        robot.clickOn("#usernamefld").write("Ime");
        robot.clickOn("#signin");
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        // Provjera teksta
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
//        assertNotNull(dialogPane.lookupAll(""));
        assertEquals("Password field is empty!",dialogPane.getContentText());

        // Klik na dugme Ok
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    void validation3(FxRobot robot){
        reset(robot);
        robot.clickOn("#usernamefld").write("Ime");
        robot.clickOn("#passwordfld").write("Ime");
        robot.clickOn("#signin");
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        // Provjera teksta
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
//        assertNotNull(dialogPane.lookupAll(""));
        assertEquals("Developer with username: \"Ime\" does not exsist!",dialogPane.getContentText());

        // Klik na dugme Ok
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    void validation4(FxRobot robot){
        reset(robot);
        robot.clickOn("#usernamefld").write("admin");
        robot.clickOn("#passwordfld").write("Ime");
        robot.clickOn("#signin");
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        // Provjera teksta
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
//        assertNotNull(dialogPane.lookupAll(""));
        assertEquals("Wrong password!",dialogPane.getContentText());

        // Klik na dugme Ok
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
    @Test
    void validation5(FxRobot robot){
        reset(robot);
        robot.clickOn("#signin");
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        // Provjera teksta
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
//        assertNotNull(dialogPane.lookupAll(""));
        assertEquals("Username field is empty!",dialogPane.getContentText());

        // Klik na dugme Ok
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }
}