package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class SignupController {

    @FXML
    public TextField namefld;

    @FXML
    public TextField surnamefld;

    @FXML
    public TextField emailfld;

    @FXML
    public TextField usernamefld;

    @FXML
    public PasswordField passwordfld;

    @FXML
    public Button signin;

    public SignupController(){
        System.out.println("SIGNUP CONTROLLER CTOR");
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    @FXML
    public void initialize(){
        namefld.getStyleClass().add("ok");
        namefld.setStyle("-fx-border-color: red");
        surnamefld.getStyleClass().add("ok");
        surnamefld.setStyle("-fx-border-color: red");
        emailfld.getStyleClass().add("ok");
        emailfld.setStyle("-fx-border-color: red");
        usernamefld.getStyleClass().add("ok");
        usernamefld.setStyle("-fx-border-color: red");
        passwordfld.getStyleClass().add("ok");
        passwordfld.setStyle("-fx-border-color: red");
        namefld.textProperty().addListener((observableValue, oldValue, newValue) ->{
            if(namefld.getText().trim().isEmpty()){
                namefld.setStyle("-fx-border-color: red");
                namefld.getStyleClass().add("ok");
            }
            else{
                namefld.setStyle("-fx-border-color: lightgreen");
                namefld.getStyleClass().add("ok");
            }
        } );

        surnamefld.textProperty().addListener((observableValue, oldValue, newValue) ->{
            if(surnamefld.getText().trim().isEmpty()){
                surnamefld.setStyle("-fx-border-color: red");
                surnamefld.getStyleClass().add("ok");
            }
            else{
                surnamefld.setStyle("-fx-border-color: lightgreen");
                surnamefld.getStyleClass().add("ok");
            }
        } );

        emailfld.textProperty().addListener((observableValue, oldValue, newValue) ->{
            if(emailfld.getText().trim().isEmpty() || !isValid(newValue)){
                emailfld.setStyle("-fx-border-color: red");
                emailfld.getStyleClass().add("ok");
            }
            else{
                emailfld.setStyle("-fx-border-color: lightgreen");
                emailfld.getStyleClass().add("ok");
            }
        } );
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

    public void ALERT(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(s + " is empty");
        alert.showAndWait();
    }
    @FXML
    public void backAction(ActionEvent actionEvent) throws IOException {
        closeWindow();
        Stage signUpStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        LoginController ctrl = new LoginController();
        loader.setController(ctrl);
        Parent root = loader.load();
        signUpStage.setTitle("Login");
        signUpStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        signUpStage.show();
    }

    @FXML
    public void signinAction(ActionEvent actionEvent) {
        System.out.println("SIGN IN ACTION");
        if(namefld.getText().trim().isEmpty()) ALERT("Name Field");
        else if(surnamefld.getText().trim().isEmpty()) ALERT("Surname Field");
        else if(emailfld.getText().trim().isEmpty()) ALERT("E-mail Field");
        else if(usernamefld.getText().trim().isEmpty()) ALERT("Username Field");
        else if(passwordfld.getText().trim().isEmpty()) ALERT("Password Field");
    }
    public void closeWindow(){
        Stage stage = (Stage) namefld.getScene().getWindow();
        stage.close();
    }
}
