package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.enums.EmptyFld;
import ba.unsa.etf.rpr.enums.StageEnums;
import ba.unsa.etf.rpr.enums.Validation;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.StageHandler;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
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

    private DeveloperDAO developerDAO;
    private Developer developer;
    private final String passwordValidation = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

    public SignupController(){
        System.out.println("SIGNUP CONTROLLER CTOR");
        developerDAO  = DeveloperDAO.getInstance();
    }
    public static boolean isValid(String email) {
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
            if(!newValue.matches(passwordValidation)){
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
        LoginController ctrl = new LoginController();
        StageHandler.loadWindow(getClass().getResource("/fxml/login.fxml"),"Sign in",ctrl);
    }


    private boolean check(){
        if(namefld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured",EmptyFld.NAME.toString()); return false;}
        else if(surnamefld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured",EmptyFld.SURNAME.toString());return false;}
        else if(emailfld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured",EmptyFld.EMAIL.toString());return false;}
        else if(!isValid(emailfld.getText())){ AlertMaker.alertERROR("Error occured", Validation.EMAIL.toString());return false;}
        else if(usernamefld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured",EmptyFld.USERNAME.toString());return false;}
        else if(passwordfld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured", EmptyFld.PASSWORD.toString());return false;}
        return true;
    }

    private boolean checkPassword(String password){
        if(password.matches(passwordValidation)) return true;
        AlertMaker.alertINFORMATION("",Validation.PASSWORD_REGEX.toString());
        return false;
    }

    @FXML
    public void signinAction(ActionEvent actionEvent) throws IOException {


        System.out.println("SIGN IN ACTION");
        if(check()) {
            if(developerDAO.findIdOfDeveloper(usernamefld.getText())!=0){
                AlertMaker.alertERROR("Error occured",StageEnums.ALREADY_EXIST.toString() + usernamefld.getText());
            }else
                if(developerDAO.findIdOfDeveloperWithEmail(emailfld.getText())!=0){
                    AlertMaker.alertERROR("Error occured", StageEnums.ALREADY_EXIST_MAIL.toString() );

                }
            else if(checkPassword(passwordfld.getText()))
             {
                System.out.println("ADDING MEMBER TO DATABASE");
                developer = new Developer(namefld.getText(),surnamefld.getText(),emailfld.getText(),usernamefld.getText(),passwordfld.getText());
                developerDAO.addDeveloper(developer);
                closeWindow();

                Stage signUpStage = new Stage();
                HomepageController ctrl = new HomepageController(developer);

                final Parent[] roots={null};

                Task<Boolean> loadingTask =new Task<> () {
                    @Override
                    protected Boolean call() {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"), ResourceBundle.getBundle("Translation"));
                        loader.setController(ctrl);
                        try {
                            roots[0] = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                };

                loadingTask.setOnSucceeded(workerStateEvent ->{

                    signUpStage.setScene(new Scene(roots[0],USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
                    signUpStage.show();
                });


                Parent secRoot = null;
                try{
                    secRoot=FXMLLoader.load(getClass().getResource("/fxml/loading.fxml"), ResourceBundle.getBundle("Translation"));
                    secRoot.setVisible(true);
                }catch(IOException e){
                    e.printStackTrace();
                }

                signUpStage.setScene(new Scene(secRoot,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));

                signUpStage.getIcons().add(new Image("/images/bug_image.png"));
                signUpStage.show();

                Thread thread = new Thread(loadingTask);
                thread.start();
            }
        }
    }

    private void changeLang() throws IOException {
        Stage stage = (Stage) usernamefld.getScene().getWindow();
        stage.setTitle(StageEnums.LOGIN.toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"), ResourceBundle.getBundle("Translation"));
        loader.setController(this);
        stage.setScene(new Scene(loader.load()));
    }

    public void switchToBS(MouseEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("bs","BA"));
        changeLang();
    }

    public void switchToEN(MouseEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("en","US"));
        changeLang();
    }

    public void closeWindow(){
        Stage stage = (Stage) namefld.getScene().getWindow();
        stage.close();
    }
}
