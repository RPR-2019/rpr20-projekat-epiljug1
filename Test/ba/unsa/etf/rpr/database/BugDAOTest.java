package ba.unsa.etf.rpr.database;

import ba.unsa.etf.rpr.model.Bug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BugDAOTest {
    ProjectDAO projectDAO = ProjectDAO.getInstance();
    DeveloperDAO developerDAO =DeveloperDAO.getInstance();
    BugDAO bugDAO =BugDAO.getInstance();

    @BeforeEach
    void def() throws SQLException {
        developerDAO.backToDefaultDatabase();
    }

    @Test
    void numberOfBugs(){
        assertEquals(1,bugDAO.getAllBugs().size());
    }

    @Test
    void editBug(){
        Bug bug = bugDAO.findBugByID(1);
        String name = bug.getBug_name();
        bug.setBug_name("BUG_NAME");
        bugDAO.editBug(bug,name,1);
        assertEquals("BUG_NAME",bugDAO.findBugByID(1).getBug_name());
    }

    @Test
    void assignedBug(){
        bugDAO.addAssign(1,1,2);
        assertEquals(1,bugDAO.getAllAssignedBugs(1,0).size());
    }

}