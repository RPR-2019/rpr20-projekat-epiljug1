package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controllers.SignupController;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.enums.StageEnums;
import javafx.scene.control.TextField;
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

class SignupControllerTest {
        DeveloperDAO developerDAO =DeveloperDAO.getInstance();

        public static boolean sadrziStil(TextField polje, String stil) {
            for (String s : polje.getStyleClass())
                if (s.equals(stil)) return true;
            return false;
        }

        @BeforeEach
        void def() throws SQLException {
            developerDAO.backToDefaultDatabase();
        }

        @Start
        public void start(Stage stage){
            SignupController ctrl = new SignupController();
            StageHandler.loadWindow(getClass().getResource("/fxml/signup.fxml"), StageEnums.LOGIN,ctrl);
            stage.toFront();
        }

    private void reset(FxRobot robot){
        robot.clickOn("#namefld").write("");
        robot.clickOn("#surnamefld").write("");
        robot.clickOn("#emailfld").write("");
        robot.clickOn("#usernamefld").write("");
        robot.clickOn("#passwordfld").write("");
    }
    @Test
    void validation(FxRobot robot){
            reset(robot);
        TextField polje1 = robot.lookup("#namefld").queryAs(TextField.class);
        assertTrue(sadrziStil(polje1, "ok"));
        assertEquals("-fx-border-color: red",polje1.getStyle());

        robot.clickOn("#namefld").write("Ime");
        assertTrue(sadrziStil(polje1, "ok"));
        assertEquals("-fx-border-color: lightgreen",polje1.getStyle());



        TextField polje2 = robot.lookup("#surnamefld").queryAs(TextField.class);
        assertTrue(sadrziStil(polje2, "ok"));
        assertEquals("-fx-border-color: red",polje2.getStyle());

        robot.clickOn("#surnamefld").write("Prezime");
        assertTrue(sadrziStil(polje2, "ok"));
        assertEquals("-fx-border-color: lightgreen",polje2.getStyle());

        TextField polje3 = robot.lookup("#emailfld").queryAs(TextField.class);
        assertTrue(sadrziStil(polje3, "ok"));
        assertEquals("-fx-border-color: red",polje3.getStyle());

        robot.clickOn("#emailfld").write("email");
        assertTrue(sadrziStil(polje3, "ok"));
        assertEquals("-fx-border-color: red",polje3.getStyle());

        robot.clickOn("#emailfld").write("@email.com");
        assertTrue(sadrziStil(polje3, "ok"));
        assertEquals("-fx-border-color: lightgreen",polje3.getStyle());


//        TextField polje4 = robot.lookup("#usernamefld").queryAs(TextField.class);
//        assertTrue(sadrziStil(polje4, "ok"));
////        assertEquals("-fx-border-color: red",polje4.getStyle());
//
//        robot.clickOn("#usernamefld").write("Username");
//        assertTrue(sadrziStil(polje4, "ok"));
//        assertEquals("-fx-border-color: lightgreen",polje4.getStyle());
//
//
//        PasswordField polje5 = robot.lookup("#passwordfld").queryAs(PasswordField.class);
//        assertTrue(sadrziStil(polje5, "ok"));
//        assertEquals("-fx-border-color: red",polje5.getStyle());
//
//        robot.clickOn("#passwordfld").write("password");
//        assertTrue(sadrziStil(polje5, "ok"));
//        assertEquals("-fx-border-color: red",polje5.getStyle());
//
//        robot.clickOn("#passwordfld").write("Password1");
//        assertTrue(sadrziStil(polje5, "ok"));
//        assertEquals("-fx-border-color: lightgreen",polje5.getStyle());
    }
}