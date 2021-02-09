package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class LoginController {
    @FXML
    public TextField usernamefld;
    @FXML
    public PasswordField passwordfld;
    @FXML
    public Button signin;
    @FXML
    public Button signup;
    @FXML
    public Label welcome;
    @FXML
    public void initialize(){
        usernamefld.getStyleClass().add("ok");
        usernamefld.setStyle("-fx-border-color: red");


        passwordfld.getStyleClass().add("ok");
        passwordfld.setStyle("-fx-border-color: red");
        usernamefld.textProperty().addListener((observableValue, oldValue, newValue) ->{
            if(usernamefld.getText().trim().isEmpty()){
                usernamefld.setStyle("-fx-border-color: red");
                usernamefld.getStyleClass().add("ok");
            }
            else{
                usernamefld.setStyle("-fx-border-color: lightgreen");
                usernamefld.getStyleClass().add("ok");
            }
        } );

        passwordfld.textProperty().addListener((observableValue, oldValue, newValue) ->{
            if(passwordfld.getText().trim().isEmpty()){
                passwordfld.setStyle("-fx-border-color: red");
                passwordfld.getStyleClass().add("ok");
            }
            else{
                passwordfld.setStyle("-fx-border-color: lightgreen");
                passwordfld.getStyleClass().add("ok");
            }
        } );
    }
    private DeveloperDAO developerDAO;
    private Developer developer;
    public LoginController(){
        developerDAO=DeveloperDAO.getInstance();

    }

    public void signinAction(ActionEvent actionEvent) throws IOException {
        if(usernamefld.getText().trim().isEmpty()){
            AlertMaker.alertERROR("Error occured","Username field is empty!");
        }else if(passwordfld.getText().trim().isEmpty()){
            AlertMaker.alertERROR("Error occured","Password fielad is empty!");
        }else if(developerDAO.findIdOfDeveloper(usernamefld.getText())==0){
            AlertMaker.alertERROR("Error occured","Developer with username: \""+usernamefld.getText()+"\" does not exsist!");
        }else {
            developer = developerDAO.findDeveloperByIDorUsername(0, usernamefld.getText());
            closeWindow();
            Stage signUpStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"));
            HomepageController ctrl = new HomepageController(developer);
            loader.setController(ctrl);
            Parent root = loader.load();
            signUpStage.setTitle("Home page");
            signUpStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            signUpStage.show();
        }
    }

    public void signupAction(ActionEvent actionEvent) throws IOException {
        closeWindow();
        Stage signUpStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        SignupController ctrl = new SignupController();
        loader.setController(ctrl);
        Parent root = loader.load();
        signUpStage.setTitle("Sign up");
        signUpStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        signUpStage.show();
    }

    public void closeWindow(){
        Stage stage = (Stage) usernamefld.getScene().getWindow();
        stage.close();
    }
}
