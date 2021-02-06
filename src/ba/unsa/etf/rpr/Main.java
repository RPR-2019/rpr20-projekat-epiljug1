package ba.unsa.etf.rpr;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException {
        DeveloperDAO developerDAO =  DeveloperDAO.getInstance();
        developerDAO.backToDefaultDatabase();

        ArrayList<Developer> dev= developerDAO.getAllDevelopers();
        for(Developer d : dev){
            System.out.println(d.getName() + " " + d.getSurname() + " " + d.getUsername());
        }

        Developer novi2 =  new Developer("test","test","test","test","test");
        developerDAO.addDeveloper(novi2);
        System.out.println("==============================================");
        ArrayList<Developer> dev2= developerDAO.getAllDevelopers();
        for(Developer d : dev2) {
            System.out.println(d.getName() + " " + d.getSurname() + " " + d.getUsername());
        }
        Project novi = new Project("nazivProjekta","opisProjekta",novi2);
        novi.setDateProjectCreated( LocalDate.of(2000,10,30)) ;
        System.out.println(novi.getDateProjectCreated());
    }

}

