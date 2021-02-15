package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.email.MailSender;
import ba.unsa.etf.rpr.model.Developer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;

public class MailSenderController {
    @FXML
    TextField senderMailFld;

    @FXML
    PasswordField senderPassFld;

    @FXML
    TextField receiverFld;

    @FXML
    TextArea textArea;

    @FXML
    TextField subjectFld;

    private String sender;
    private String receiver;

    public MailSenderController(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    @FXML
    public void initialize(){
        senderMailFld.setText(sender);
        receiverFld.setText(receiver);
    }

    @FXML
    public void sendBtnAction(ActionEvent actionEvent){
        if(senderPassFld.getText().trim().isEmpty()) AlertMaker.alertERROR("Error occured","Password field is empty!");
        else
            if(textArea.getText().trim().isEmpty()) AlertMaker.alertERROR("Error occured","Text area is empty!");
        else{
                try {
                    MailSender.sendEmail(sender,senderPassFld.getText(),receiver,subjectFld.getText(),textArea.getText());
                    AlertMaker.alertINFORMATION("Successfuly sended","Your mail is successfuly sended");
                    textArea.setText("");
                } catch (MessagingException e) {
                    AlertMaker.alertERROR("Error occured!","Something went wrong while sending! Please check your info");
                }
            }
    }

}
