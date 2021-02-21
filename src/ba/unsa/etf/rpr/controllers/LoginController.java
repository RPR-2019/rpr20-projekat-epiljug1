package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.StageHandler;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
    public AnchorPane anchorPane;

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
        if(usernamefld.getText().trim().isEmpty())
            AlertMaker.alertERROR("Error occured","Username field is empty!");
        else if(passwordfld.getText().trim().isEmpty())
            AlertMaker.alertERROR("Error occured","Password field is empty!");
        else if(developerDAO.findIdOfDeveloper(usernamefld.getText())==0)
            AlertMaker.alertERROR("Error occured","Developer with username: \""+usernamefld.getText()+"\" does not exsist!");
        else {
            developer = developerDAO.findDeveloperByIDorUsername(0, usernamefld.getText());
            closeWindow();

            Stage homePageStage = new Stage();
            HomepageController ctrl = new HomepageController(developer);

            final Parent[] roots={null};

            Task<Boolean> loadingTask =new Task<> () {
                @Override
                protected Boolean call() {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"));
                    loader.setController(ctrl);
                    try {
                        roots[0] = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            };

            loadingTask.setOnSucceeded(workerStateEvent ->{
                homePageStage.setScene(new Scene(roots[0],USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
                homePageStage.show();
            });


            Parent secRoot = null;
            try{
                secRoot=FXMLLoader.load(getClass().getResource("/fxml/loading2.fxml"));
                secRoot.setVisible(true);
            }catch(IOException e){
                e.printStackTrace();
            }

            homePageStage.setScene(new Scene(secRoot,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
            homePageStage.show();

            Thread thread = new Thread(loadingTask);
            thread.start();

//            Stage signUpStage = new Stage();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homepage.fxml"));
//            HomepageController ctrl = new HomepageController(developer);
//            loader.setController(ctrl);
//            Parent root = loader.load();
//            signUpStage.setTitle("Home page");
//            signUpStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
//            signUpStage.show();
        }
    }

    public void signupAction(ActionEvent actionEvent) throws IOException {

        closeWindow();



//        Stage signUpStage = new Stage();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        SignupController ctrl = new SignupController();
        StageHandler.loadWindow(getClass().getResource("/fxml/signup.fxml"),"Sign up",ctrl);
//        loader.setController(ctrl);
//        Parent root = loader.load();
//        signUpStage.setTitle("Sign up");
//        signUpStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
//        signUpStage.show();
    }

    @FXML
    public void closeAction(ActionEvent actionEvent){ closeWindow();}

    public void closeWindow(){
       ((Stage) usernamefld.getScene().getWindow()).close();
    }


    private void promijeniJezik() throws IOException {
        Stage stage = (Stage) usernamefld.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), ResourceBundle.getBundle("Translation"));
        loader.setController(this);
        stage.setScene(new Scene(loader.load()));
    }

    public void switchToBS(MouseEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("bs","BA"));
        promijeniJezik();
    }

    public void switchToEN(MouseEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("en","US"));
        promijeniJezik();
    }


}
