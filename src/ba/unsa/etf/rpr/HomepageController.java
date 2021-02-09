package ba.unsa.etf.rpr;

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
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public HomepageController(Developer developer){
        this.developer=developer;
    }


    @FXML
    public void initialize(){
        user.setText(developer.getName()+ " "+developer.getSurname());
        date.setText(LocalDate.now().format(myFormatObj));
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("");
        alert.setContentText("Do you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();

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
    public void closeWindow(){
        Stage stage = (Stage) btnAddProject.getScene().getWindow();
        stage.close();
    }
}
