package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DeveloperDAO {
    private static DeveloperDAO instance = null;
    private static Connection conn;
    private PreparedStatement getAllDevelopers;

    public static Connection getConn() {
        return conn;
    }

    public static DeveloperDAO getInstance() {
        if(instance==null) instance = new DeveloperDAO();
        return instance;
    }

    private DeveloperDAO(){
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:BugTracker.db");
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            getAllDevelopers = conn.prepareStatement("SELECT * FROM developer");
        }catch (SQLException e){
            createDataBase();
            try{
                getAllDevelopers = conn.prepareStatement("SELECT * FROM developer");
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }
    }

    private void createDataBase() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner( new FileInputStream("BugTracker.db.sql"));
            String sqlUpit="";
            while(ulaz.hasNext()){
                sqlUpit+=ulaz.nextLine();
                if(sqlUpit.charAt(sqlUpit.length()-1)==';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit="";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
