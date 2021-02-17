package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.StageHandler;
import ba.unsa.etf.rpr.alert.AlertMaker;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.database.DeveloperDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ListDevelopersController {
        @FXML
        BorderPane mainStage;

        @FXML
        TableView<Developer> tableViewDevelopers;
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
        private DeveloperDAO developerDAO;
        private ProjectDAO projectDAO;
        private Developer developer;


        public ListDevelopersController(Developer developer){
            this.developer=developer;
            developerDAO = DeveloperDAO.getInstance();
            projectDAO = ProjectDAO.getInstance();
            listDevelopers = FXCollections.observableArrayList(developerDAO.getAllDevelopers(developerDAO.findIdOfDeveloper(developer.getUsername())));
        }


        @FXML
        public void initialize(){
            tableViewDevelopers.setItems(listDevelopers);
            colName.setCellValueFactory(new PropertyValueFactory("name"));
            colSurname.setCellValueFactory(new PropertyValueFactory("surname"));
            colUsername.setCellValueFactory(new PropertyValueFactory("username"));
            colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        }
        @FXML
        public void showDeveloperAction(ActionEvent actionEvent) throws IOException {
            if(tableViewDevelopers.getSelectionModel().getSelectedItem()!=null) {
                Stage stage = (Stage) tableViewDevelopers.getScene().getWindow();
                stage.close();
                Stage signUpStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/showDeveloper.fxml"));
                ShowDeveloperController ctrl = new ShowDeveloperController(tableViewDevelopers.getSelectionModel().getSelectedItem());
                loader.setController(ctrl);
                Parent root = loader.load();
                signUpStage.setTitle("Developer review");
                signUpStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                signUpStage.show();
                signUpStage.setOnHiding(windowEvent -> {
                    stage.show();
                });
            }else AlertMaker.alertERROR("Error occured!","You did not selected any developer!");

        }
        @FXML
        public void  sendMailAction(ActionEvent actionEvent){
            if(tableViewDevelopers.getSelectionModel().getSelectedItem()!=null){
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
        public void searchAction(ActionEvent actionEvent){
// listSearchDevelopers = FXCollections.observableArrayList(projectDAO.searchForDevelopers(searchFld.getText().trim(),project.getCreator().getUsername()));
            if(!searchFld.getText().trim().isEmpty()){
                listDevelopers.setAll(projectDAO.searchForDevelopers(searchFld.getText().trim(),developer.getUsername()));
                tableViewDevelopers.refresh();
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
