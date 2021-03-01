package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.LoadWebPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AboutAppController {
    public  AboutAppController(){}

    private final String LINK_OF_GITHUB_PROJECT = "https://github.com/RPR-2019/rpr20-projekat-epiljug1";

    @FXML
    public void sourceCodeLinkAction(ActionEvent actionEvent){
        LoadWebPage.loadWebpage(LINK_OF_GITHUB_PROJECT);
    }
}
