package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BugDAO {

    private static Connection conn;
    private static BugDAO instance = null;
    private ProjectDAO instanceProjectDAO = ProjectDAO.getInstance();

    private PreparedStatement getAllBugs,findBugByID, addBug, findMax ;

    public static Connection getConn() {
        return conn;
    }

    public static BugDAO getInstance() {
        if(instance==null) instance = new BugDAO();
        return instance;
    }

    private BugDAO(){
        try{
            //connecting with database
            conn = DriverManager.getConnection("jdbc:sqlite:BugTracker.db");
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            getAllBugs = conn.prepareStatement("SELECT * FROM bug");
        }catch (SQLException e){
            createDataBase();
            try{
                getAllBugs = conn.prepareStatement("SELECT * FROM bug");
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }

        try{
            findBugByID = conn.prepareStatement("SELECT * FROM bug WHERE bug_id = ?");
            findMax = conn.prepareStatement("SELECT Max(bug_id) from bug");
            addBug = conn.prepareStatement("INSERT into bug values(?,?,?,?,?,?,?)");
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
    private int maxIndex(){
        try {
            ResultSet rs = findMax.executeQuery();
            return  rs.getInt(1) + 1;
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 1;
    }
    public Bug findBugByID(int id){
        Bug novi = null;
        try{
            findBugByID.setInt(1,id);
            ResultSet rs = findBugByID.executeQuery();
            novi = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),instanceProjectDAO.findProject("",rs.getInt(6)),rs.getString(7));
            novi.setDate_created(instanceProjectDAO.getDate(rs.getString(5)));
            return  novi;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return novi;
    }
    public void addNewBug(Bug newBug){
        try{
            addBug.setInt(1,maxIndex());
            addBug.setString(2,newBug.getBug_name());
            addBug.setString(3,newBug.getBug_type());
            addBug.setString(4,newBug.getStatus());
            addBug.setString(5,newBug.getDate_created());
            addBug.setInt(6,instanceProjectDAO.findID(newBug.getProject()));
            addBug.setString(7,newBug.getComplexity());
            addBug.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public ArrayList<Bug> getAllBugs(){
        ArrayList<Bug> allBugs = new ArrayList<>();
        try{
            ResultSet rs = getAllBugs.executeQuery();
            while (rs.next()){
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),instanceProjectDAO.findProject("",rs.getInt(6)),rs.getString(7));
                newBug.setDate_created(instanceProjectDAO.getDate(rs.getString(5)));
                allBugs.add(newBug);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allBugs;
    }
}
