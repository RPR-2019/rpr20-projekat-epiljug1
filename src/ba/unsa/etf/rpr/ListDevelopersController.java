package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ListDevelopersController {

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

        private ObservableList<Developer> listDevelopers;
        private DeveloperDAO developerDAO;
        private Developer developer;


        public ListDevelopersController(Developer developer){
            this.developer=developer;
            developerDAO = DeveloperDAO.getInstance();
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
            signUpStage.setOnHiding( windowEvent -> {
                stage.show();
            });

        }

        public void setDeveloper(Developer developer) {
            this.developer = developer;
        }


        public void closeWindow(javafx.event.ActionEvent actionEvent){
            Stage stage = (Stage) tableViewDevelopers.getScene().getWindow();
            stage.close();
        }



    }
