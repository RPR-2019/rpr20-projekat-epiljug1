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
import javafx.scene.control.*;
import javafx.scene.image.Image;
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


    private DeveloperDAO developerDAO;
    private Developer developer;


    public LoginController(){
        developerDAO=DeveloperDAO.getInstance();

    }


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


    public void signinAction(ActionEvent actionEvent) throws IOException {
        if(usernamefld.getText().trim().isEmpty())
            AlertMaker.alertERROR("Error occured", EmptyFld.USERNAME.toString());
        else if(passwordfld.getText().trim().isEmpty())
            AlertMaker.alertERROR("Error occured",EmptyFld.PASSWORD.toString());
        else if(developerDAO.findIdOfDeveloper(usernamefld.getText())==0)
            if(Locale.getDefault().getCountry().equals("US"))
                AlertMaker.alertERROR("Error occured","Developer with username: \""+usernamefld.getText()+"\" does not exsist!");
            else
                AlertMaker.alertERROR("Error occured","Developer sa korisničkim imenom: \""+usernamefld.getText()+"\" ne postoji!");
        else {
            System.out.println("USERNAME >"  + usernamefld.getText() + "<");
            System.out.println("PASSWORD >" + passwordfld.getText() + "<");
            developer = developerDAO.loginGetDeveloper(usernamefld.getText(),passwordfld.getText());
            if(developer==null){
                AlertMaker.alertERROR("Error occured", Validation.PASSWORD.toString());
                return;
            }
            closeWindow();

            Stage homePageStage = new Stage();
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
            homePageStage.setTitle(StageEnums.HOME_PAGE.toString());
            homePageStage.getIcons().add(new Image("/images/bug_image.png"));
            homePageStage.show();

            Thread thread = new Thread(loadingTask);
            thread.start();


        }
    }

    public void signupAction(ActionEvent actionEvent) throws IOException {
        closeWindow();
        SignupController ctrl = new SignupController();
        StageHandler.loadWindow(getClass().getResource("/fxml/signup.fxml"), StageEnums.LOGIN,ctrl);
    }

    @FXML
    public void closeAction(ActionEvent actionEvent){ closeWindow();}

    public void closeWindow(){
       ((Stage) usernamefld.getScene().getWindow()).close();
    }


    private void changeLang() throws IOException {
        Stage stage = (Stage) usernamefld.getScene().getWindow();
        stage.setTitle(StageEnums.LOGIN.toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), ResourceBundle.getBundle("Translation"));
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


}
