package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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


        public void setDeveloper(Developer developer) {
            this.developer = developer;
        }


        public void closeWindow(javafx.event.ActionEvent actionEvent){
            Stage stage = (Stage) tableViewDevelopers.getScene().getWindow();
            stage.close();
        }



    }
