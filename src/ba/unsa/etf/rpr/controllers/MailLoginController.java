package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.model.Developer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MailLoginController {

    @FXML
    public TextField emailSender;

    @FXML
    public PasswordField passwordSender;

    private Developer developer;

    public MailLoginController(Developer developer){
        this.developer=developer;
    }


    @FXML
    public void initialize(){
        emailSender.setText(developer.getEmail());
    }

    @FXML
    public void okAction(ActionEvent actionEvent){
        if(emailSender.getText().trim().isEmpty() || !SignupController.isValid(emailSender.getText())  || passwordSender.getText().trim().isEmpty() ) AlertMaker.alertERROR("Error occured","Your info is invalid!");
        ((Stage) emailSender.getScene().getWindow()).close();
    }

    public String getEmail(){
        return emailSender.getText();
    }

    public String getPassword(){
        return passwordSender.getText();
    }
}
