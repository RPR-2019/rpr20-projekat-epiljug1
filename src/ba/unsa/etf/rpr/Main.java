package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
//        DeveloperDAO developerDAO =  DeveloperDAO.getInstance();
//        ArrayList<Developer> dev= developerDAO.getAllDevelopers();
//        for(Developer d : dev){
//            System.out.println(d.getName() + " " + d.getSurname() + " " + d.getUsername());
//        }
//
//        Developer novi =  new Developer("test","test","test","test","test");
//        developerDAO.addDeveloper(novi);
//        System.out.println("==============================================");
//        ArrayList<Developer> dev2= developerDAO.getAllDevelopers();
//        for(Developer d : dev2) {
//            System.out.println(d.getName() + " " + d.getSurname() + " " + d.getUsername());
//        }
        Project novi = new Project("nazivProjekta","opisProjekta");
        novi.setDateProjectCreated( LocalDate.of(2000,10,30)) ;
        System.out.println(novi.getDateProjectCreated());
    }

}

