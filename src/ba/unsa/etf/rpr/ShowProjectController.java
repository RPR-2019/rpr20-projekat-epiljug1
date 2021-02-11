package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class ShowProjectController {
    @FXML
    PieChart pie;

    @FXML
    TextField nameFld;
    @FXML
    TextField creatorFld;
    @FXML
    TextField clientFld;
    @FXML
    TextField clientEmailFld;
    @FXML
    TextField dateFld;
    @FXML
    TextArea descFld;



    private Project project;
    private ProjectDAO projectDAO;

    public ShowProjectController(Project project){
        this.project = project;
        projectDAO = ProjectDAO.getInstance();

    }

    @FXML
    public void initialize(){
        pie.setData(projectDAO.getProjectGraphStatistic(project));
        nameFld.setText(project.getName());
        creatorFld.setText(project.getCreator().toString());
        clientFld.setText(project.getClient_name());
        clientEmailFld.setText(project.getClient_email());
        dateFld.setText(project.getDateProjectCreated());
        descFld.setText(project.getDescription());
    }

}
