package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.enums.Validation;
import ba.unsa.etf.rpr.reports.ReportsHandler;
import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.enums.Placeholders;
import ba.unsa.etf.rpr.model.Developer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListDevelopersController {
        @FXML
        public BorderPane mainStage;

        @FXML
        public TableView<Developer> tableViewDevelopers;

        @FXML
        public TableColumn colName;

        @FXML
        public TableColumn colSurname;

        @FXML
        public TableColumn colUsername;

        @FXML
        public TableColumn colEmail;

        @FXML
        public TextField searchFld;

        private ObservableList<Developer> listDevelopers;
        private ObservableList<Developer> listAllDevelopers;
        private DeveloperDAO developerDAO;
        private ProjectDAO projectDAO;
        private Developer developer;


        public ListDevelopersController(Developer developer){
            this.developer=developer;
            developerDAO = DeveloperDAO.getInstance();
            projectDAO = ProjectDAO.getInstance();
            listDevelopers = FXCollections.observableArrayList(developerDAO.getAllDevelopers(developerDAO.findIdOfDeveloper(developer.getUsername())));
            listAllDevelopers = FXCollections.observableArrayList(developerDAO.getAllDevelopers(developerDAO.findIdOfDeveloper(developer.getUsername())));
        }


        @FXML
        public void initialize(){
            tableViewDevelopers.setItems(listDevelopers);
            colName.setCellValueFactory(new PropertyValueFactory("name"));
            colSurname.setCellValueFactory(new PropertyValueFactory("surname"));
            colUsername.setCellValueFactory(new PropertyValueFactory("username"));
            colEmail.setCellValueFactory(new PropertyValueFactory("email"));

            searchFld.textProperty().addListener((observableValue, oldValue, newValue) ->{
                if(!newValue.getBytes().toString().trim().isEmpty()){

                    listDevelopers.setAll(listAllDevelopers.stream().filter(developer1 -> {
                        return  developer1.getName().toLowerCase().contains(newValue.toLowerCase()) || developer1.getSurname().toLowerCase().contains(newValue.toLowerCase()) ||developer1.getUsername().toLowerCase().contains(newValue.toLowerCase()) || developer1.getEmail().toLowerCase().contains(newValue.toLowerCase());
                    }).collect(Collectors.toCollection(ArrayList::new)));
                    tableViewDevelopers.refresh();
                }else{
                    listDevelopers.setAll(listAllDevelopers);
                    tableViewDevelopers.refresh();
                }
            });
            tableViewDevelopers.setPlaceholder(new Label(Placeholders.DEVELOPERS.toString()));
        }

        private boolean check(TableView tableView){
            if(tableView.getSelectionModel().getSelectedItem()!=null) return true;
            AlertMaker.alertERROR("Error occured!", Validation.SELECT.toString());
            return false;
        }

        @FXML
        public void showDeveloperAction(ActionEvent actionEvent) throws IOException {
            if(check(tableViewDevelopers)) {
                Stage stage = (Stage) tableViewDevelopers.getScene().getWindow();
                stage.close();
                ShowDeveloperController ctrl = new ShowDeveloperController(tableViewDevelopers.getSelectionModel().getSelectedItem());
                Stage signUpStage = StageHandler.loadWindow(getClass().getResource("/fxml/showDeveloper.fxml"),"Developer review",ctrl);
                signUpStage.setOnHiding(windowEvent -> {
                    stage.show();
                });
            }

        }
        @FXML
        public void  sendMailAction(ActionEvent actionEvent){
            if(check(tableViewDevelopers)){
                MailSenderController mailSenderController = new MailSenderController(developer.getEmail(),tableViewDevelopers.getSelectionModel().getSelectedItem().getEmail());
                BoxBlur blur = new BoxBlur(3, 3, 3);
                mainStage.setEffect(blur);
                Stage stage = StageHandler.loadWindow(getClass().getResource("/fxml/mailSender.fxml"),"Send mail",mailSenderController);
                stage.setOnHiding(windowEvent -> {
                    mainStage.setEffect(null);
                });
            }
        }


        @FXML
        public void exportAction(ActionEvent actionEvent){
                try {
                    new ReportsHandler().showReport(developerDAO.getConn(),developerDAO.findIdOfDeveloper(developer.getUsername()));
                } catch (JRException e1) {
                    e1.printStackTrace();
                }
        }
        public void setDeveloper(Developer developer) {
            this.developer = developer;
        }


        public void closeWindow(javafx.event.ActionEvent actionEvent){
            Stage stage = (Stage) tableViewDevelopers.getScene().getWindow();
            stage.close();
        }



    }
