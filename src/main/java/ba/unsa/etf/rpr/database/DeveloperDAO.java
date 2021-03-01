package ba.unsa.etf.rpr.database;

import ba.unsa.etf.rpr.model.Developer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DeveloperDAO {
    private static DeveloperDAO instance = null;
    private static Connection conn;
    private PreparedStatement getAllDevelopers,  addDeveloper, findMax,
            findDeveloperByIdorUsername, findId, getAllProjectsForDeveloper,getAllDevelopersInsteadOfOne
            ,checkUsername,checkEmail,editProfile,login,findIdWithEmail;

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

            addDeveloper = conn.prepareStatement("INSERT INTO developer values(?,?,?,?,?,?)");
            findMax = conn.prepareStatement("SELECT Max(developer_id) from developer");
            findDeveloperByIdorUsername = conn.prepareStatement("SELECT * FROM developer where   developer_id=? or username = ? ");
            findId = conn.prepareStatement("SELECT developer_id from developer where username=?");
            findIdWithEmail= conn.prepareStatement("SELECT developer_id from developer where email=?");
            getAllProjectsForDeveloper = conn.prepareStatement("SELECT DISTINCT project.*\n" +
                    "FROM project, connections, developer\n" +
                    "WHERE project_id=connections.pr_id AND connections.de_id=developer_id AND developer_id=?;\n");
            getAllDevelopersInsteadOfOne = conn.prepareStatement("select * from developer where developer.developer_id!=?");
            checkUsername = conn.prepareStatement("SELECT Count(*) FROM developer WHERE developer_id!=? AND username=?  ");
            checkEmail = conn.prepareStatement("SELECT Count(*) FROM developer WHERE developer_id!=? AND email=?  ");
            editProfile  = conn.prepareStatement("UPDATE developer set ime=?, prezime=?, email=?, username=?,password=? WHERE developer_id=?");
            login = conn.prepareStatement("SELECT * FROM developer WHERE username=? AND password=?");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void createDataBase() {
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

    public static void backToDefault(){
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM bug_assigned");
            stmt.executeUpdate("DELETE FROM connections");
            stmt.executeUpdate("DELETE FROM bug");
            stmt.executeUpdate("DELETE FROM project");
            stmt.executeUpdate("DELETE FROM developer");
            createDataBase();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }



    public ArrayList<Developer> getAllDevelopers(int id){
        ArrayList<Developer> developers = new ArrayList<>();
        try{
            getAllDevelopersInsteadOfOne.setInt(1,id);
            ResultSet rs = getAllDevelopersInsteadOfOne.executeQuery();
            while(rs.next()) {
                developers.add(new Developer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
               // System.out.println("RS1 = " + rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return developers;
    }

    public ArrayList<Developer> getAllDevelopers(){
        ArrayList<Developer> developers = new ArrayList<>();
        try{
            ResultSet rs = getAllDevelopers.executeQuery();
            while(rs.next()) {
                developers.add(new Developer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
               // System.out.println("RS1 = " + rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return developers;
    }
    public Developer findDeveloperByIDorUsername(int id,String username){
        Developer novi = null;
        try {
            findDeveloperByIdorUsername.setInt(1,id);
            findDeveloperByIdorUsername.setString(2,username);
            ResultSet rs = findDeveloperByIdorUsername.executeQuery();
            if(rs.next())
                novi = new Developer(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        if(novi==null) return new Developer("","","","","");
        return novi;
    }

    public Developer loginGetDeveloper(String username,String password){
        Developer novi = null;
        try {
            login.setString(1,username);
            login.setString(2,password);
            ResultSet rs = login.executeQuery();
            if(rs.next())
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
    public int findIdOfDeveloper(String username){
        try{
            findId.setString(1,username);
            ResultSet rs = findId.executeQuery();
            if(rs.next())return rs.getInt(1);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return 0;
    }
    public int findIdOfDeveloperWithEmail(String email){
        try{
            findIdWithEmail.setString(1,email);
            ResultSet rs = findIdWithEmail.executeQuery();
            if(rs.next())return rs.getInt(1);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return 0;
    }

    public boolean checkUsername(int developerID, String username){
        try{
            checkUsername.setInt(1,developerID);
            checkUsername.setString(2,username);
            ResultSet rs = checkUsername.executeQuery();
            if(rs.next())
                if(rs.getInt(1)!=0) return false;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return true;
    }

    public boolean checkEmail(int developerID, String email){
        try{
            checkEmail.setInt(1,developerID);
            checkEmail.setString(2,email);
            ResultSet rs = checkEmail.executeQuery();
            if(rs.next())
                if(rs.getInt(1)!=0) return false;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return true;
    }

    public void editProfileInfo(Developer developer, int developerID){
        try{
            editProfile.setString(1,developer.getName());
            editProfile.setString(2,developer.getSurname());
            editProfile.setString(3,developer.getEmail());
            editProfile.setString(4,developer.getUsername());
            editProfile.setString(5,developer.getPassword());
            editProfile.setInt(6,developerID);
            editProfile.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
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

    public void backToDefaultDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM connections");
        stmt.executeUpdate("DELETE FROM bug");
        stmt.executeUpdate("DELETE FROM project");
        stmt.executeUpdate("DELETE FROM developer");

        createDataBase();
    }

    public void connect(){
        try{
            //connecting with database
            conn = DriverManager.getConnection("jdbc:sqlite:BugTracker.db");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
