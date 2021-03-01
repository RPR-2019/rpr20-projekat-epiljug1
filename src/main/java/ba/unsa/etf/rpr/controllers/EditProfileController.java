package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.enums.EmptyFld;
import ba.unsa.etf.rpr.enums.StageEnums;
import ba.unsa.etf.rpr.enums.Validation;
import ba.unsa.etf.rpr.model.Developer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class EditProfileController {
    @FXML
    public TextField nameFld;

    @FXML
    public TextField usernameFld;

    @FXML
    public TextField emailFld;

    @FXML
    public TextField surnameFld;

    @FXML
    public PasswordField passFld;

    private Developer developer;
    private DeveloperDAO developerDAO;
    private int developerID;
    private final String passwordValidation = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";


    private boolean checkPassword(String password){
        if(password.matches(passwordValidation)) return true;
        AlertMaker.alertINFORMATION("",Validation.PASSWORD_REGEX.toString());
        return false;
    }


    public EditProfileController(Developer developer){
        this.developer=developer;
        developerDAO= DeveloperDAO.getInstance();
        developerID = developerDAO.findIdOfDeveloper(developer.getUsername());
    }

    @FXML
    public void initialize(){
        nameFld.setText(developer.getName());
        surnameFld.setText(developer.getSurname());
        usernameFld.setText(developer.getUsername());
        emailFld.setText(developer.getEmail());
        passFld.setText(developer.getPassword());
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
    public void saveAction(ActionEvent actionEvent){


        if(check() &&  checkPassword(passFld.getText())){
            if(!isValid(emailFld.getText())) {
                AlertMaker.alertERROR("Error occured", Validation.EMAIL.toString());
                return;
            }
            if(!developer.getUsername().equals(usernameFld.getText()) && developerDAO.findIdOfDeveloper(usernameFld.getText())!=0){
                AlertMaker.alertERROR("Error occured", StageEnums.ALREADY_EXIST.toString()  + usernameFld.getText());
                return;
            }
            if(!developer.getEmail().equals(emailFld.getText()) && developerDAO.findIdOfDeveloperWithEmail(emailFld.getText())!=0){
                AlertMaker.alertERROR("Error occured", StageEnums.ALREADY_EXIST_MAIL.toString() );
                return;
            }

            if(!developerDAO.checkUsername(developerID,usernameFld.getText())){
                AlertMaker.alertERROR("Error occured", Validation.USERNAME_ERROR.toString());
                return;
            }
            if(!developerDAO.checkEmail(developerID,emailFld.getText())){
                AlertMaker.alertERROR("Error occured", Validation.EMAIL_ERROR.toString());
                return;
            }
            Developer edit = new Developer(nameFld.getText(),surnameFld.getText(),emailFld.getText(),usernameFld.getText(),passFld.getText());
            developerDAO.editProfileInfo(edit, developerID);
            closeAction(actionEvent);
        }
    }

    @FXML
    public void closeAction(ActionEvent actionEvent){
        ((Stage)nameFld.getScene().getWindow()).close();
    }


    private boolean check(){
        if(nameFld.getText().trim().isEmpty()){ AlertMaker.alertERROR("Error occured", EmptyFld.NAME.toString()); return false;}
        if(surnameFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured",EmptyFld.SURNAME.toString());  return false;}
        if(usernameFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured",EmptyFld.USERNAME.toString());  return false;}
        if(emailFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured",EmptyFld.EMAIL.toString());  return false;}
        if(passFld.getText().trim().isEmpty()) {AlertMaker.alertERROR("Error occured",EmptyFld.PASSWORD.toString());  return false;}
        return  true;
    }

}
