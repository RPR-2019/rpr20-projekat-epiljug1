package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.database.DeveloperDAO;
import ba.unsa.etf.rpr.model.Developer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperDAOTest {

    DeveloperDAO developerDAO = DeveloperDAO.getInstance();

    @BeforeEach
    void def() throws SQLException {
        developerDAO.backToDefaultDatabase();
    }

    @Test
    void getAllDev(){
        assertEquals(2,developerDAO.getAllDevelopers().size());
    }

    @Test
    void findDeveloper(){
        Developer developer = developerDAO.findDeveloperByIDorUsername(0,"admin");
        assertEquals("Evelin",developer.getName());
        Developer developer2 = developerDAO.findDeveloperByIDorUsername(1,"");
        assertEquals("Evelin",developer2.getName());
    }

    @Test
    void login(){
        Developer developer = developerDAO.loginGetDeveloper("test","test");
        Developer developer2 = developerDAO.loginGetDeveloper("admin","test");
        Developer developer3 = developerDAO.loginGetDeveloper("test","Password1");
        assertNull(developer);
        assertNull(developer2);
        assertNull(developer3);
    }

    @Test
    void editProfile(){
        Developer newDev= new Developer("Evelin","Piljug","piljugevelin28@gmail.com","ADMIN","Password1");
        developerDAO.editProfileInfo(newDev,1);
        Developer developer = developerDAO.findDeveloperByIDorUsername(1,"");
        assertEquals("ADMIN",developer.getUsername());
    }
}