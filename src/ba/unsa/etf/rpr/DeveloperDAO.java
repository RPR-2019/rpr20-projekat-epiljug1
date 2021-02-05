package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DeveloperDAO {
    private static DeveloperDAO instance = null;
    private static Connection conn;
    private PreparedStatement getAllDevelopers,findDeveloperWithUsername, addDeveloper, findMax;

    public static Connection getConn() {
        return conn;
    }

    public static DeveloperDAO getInstance() {
        if(instance==null) instance = new DeveloperDAO();
        return instance;
    }

    private DeveloperDAO(){
        try{
            //connecting with database
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

        try{
            findDeveloperWithUsername = conn.prepareStatement("SELECT * FROM developer where username=?");
            addDeveloper = conn.prepareStatement("INSERT INTO developer values(?,?,?,?,?,?)");
            findMax = conn.prepareStatement("SELECT Max(developer_id) from developer");
        }catch (SQLException e){
            e.printStackTrace();
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

    public ArrayList<Developer> getAllDevelopers(){
        ArrayList<Developer> developers = new ArrayList<>();
        try{
            ResultSet rs = getAllDevelopers.executeQuery();
            while(rs.next()) {
                developers.add(new Developer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
                System.out.println("RS1 = " + rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return developers;
    }

    public Developer findDeveloper(String username){
        Developer novi = null;
        try {
            findDeveloperWithUsername.setString(1,username);
            ResultSet rs = findDeveloperWithUsername.executeQuery();
            novi = new Developer(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return novi;
    }
    private int maxIndex(){
        try {
            ResultSet rs = findMax.executeQuery();
            return  rs.getInt(1) + 1;
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 1;
    }


    public void addDeveloper(Developer developer){
        try {
            addDeveloper.setInt(1,maxIndex());
            addDeveloper.setString(2, developer.getName());
            addDeveloper.setString(3, developer.getSurname());
            addDeveloper.setString(4, developer.getEmail());
            addDeveloper.setString(5, developer.getUsername());
            addDeveloper.setString(6, developer.getPassword());
            addDeveloper.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

    }


}
