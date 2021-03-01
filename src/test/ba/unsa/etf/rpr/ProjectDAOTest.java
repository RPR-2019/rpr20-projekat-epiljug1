package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.database.ProjectDAO;
import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDAOTest {
    ProjectDAO projectDAO = ProjectDAO.getInstance();
    DeveloperDAO developerDAO =DeveloperDAO.getInstance();

    @BeforeEach
    void def() throws SQLException {
        developerDAO.backToDefaultDatabase();
    }

    @Test
    void addNewProject(){
        Developer developer = developerDAO.findDeveloperByIDorUsername(1,"");
        projectDAO.addNewProject(new Project("Name","description",developer,"Client Name","Client email", "sourceCode"));
        ArrayList<Project> allProjectOfDev = projectDAO.getAllProjectsOfDeveloper(developer);
        assertEquals("Name",allProjectOfDev.get(1).getName());

        ArrayList<Project> allProjectForDev = projectDAO.getAllProjectsForDeveloper(developer);
        assertEquals(0,allProjectForDev.size());
    }

    @Test
    void updateProject(){
        Developer developer = developerDAO.findDeveloperByIDorUsername(1,"");
        Project project = projectDAO.findProject("",1);
        project.setName("TEST_UPDATE_PROJECT");
        projectDAO.updateProject(1,project.getName(),project.getDescription(),project.getClient_name(),project.getClient_email(),project.getCode_link());
        Assertions.assertEquals("TEST_UPDATE_PROJECT",projectDAO.findProject("",1).getName());
    }

    @Test
    void addDeveloperOnProject(){
        projectDAO.addDeveloperOnProject(1,2);
        assertEquals(1,projectDAO.getAllDevelopersWhoWorksOnAProject(1).size());
    }

}