package ba.unsa.etf.rpr.database;

import ba.unsa.etf.rpr.model.Bug;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BugDAO {

    private  Connection conn;
    private static BugDAO instance = null;
    private ProjectDAO instanceProjectDAO = ProjectDAO.getInstance();
    public void backToDefaultDatabase() throws SQLException {
        instanceProjectDAO.backToDefaultDataBase();
    }

    private PreparedStatement getAllBugs,findBugByID, addBug, findMax,getAllBugsForProject, getBugReguest, approveRequest, denyRequest;

    public  Connection getConn() {
        return conn;
    }

    public static BugDAO getInstance() {
        if(instance==null) instance = new BugDAO();
        return instance;
    }

    private BugDAO(){
        try{
            //connecting with database
            if(DeveloperDAO.getConn()!=null)
                conn = DeveloperDAO.getConn();
            else
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
            addBug = conn.prepareStatement("INSERT into bug values(?,?,?,?,?,?,?,?,?)");
            getAllBugsForProject = conn.prepareStatement("SELECT * from bug where projectID=?");
            getBugReguest = conn.prepareStatement("SELECT DISTINCT bug.* from bug, project where project.project_id=? and project.project_id=bug.projectID and bug.solver_id==0 and bug.request_id!=0");
            approveRequest = conn.prepareStatement("UPDATE bug SET status='fixed', solver_id=?, request_id=0 where bug_name=? and projectID=?");
            denyRequest = conn.prepareStatement("UPDATE bug SET request_id=0 where bug_name=? and projectID=?");
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
            novi = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),instanceProjectDAO.findProject("",rs.getInt(6)),rs.getString(7),rs.getInt(8));
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
            addBug.setInt(8,newBug.getSolver_id());
            addBug.setInt(9,0);
            addBug.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public ArrayList<Bug> getAllBugsForProject(int id){
        ArrayList<Bug> allBugs = new ArrayList<>();
        try{
            getAllBugsForProject.setInt(1,id);
            ResultSet rs = getAllBugsForProject.executeQuery();
            while (rs.next()){
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),instanceProjectDAO.findProject("",rs.getInt(6)),rs.getString(7),rs.getInt(8));
                newBug.setDate_created(instanceProjectDAO.getDate(rs.getString(5)));
                newBug.setRequest_id(rs.getInt(9));
                allBugs.add(newBug);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allBugs;
    }

    public ArrayList<Bug> getAllBugs(){
        ArrayList<Bug> allBugs = new ArrayList<>();
        try{
            ResultSet rs = getAllBugs.executeQuery();
            while (rs.next()){
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),instanceProjectDAO.findProject("",rs.getInt(6)),rs.getString(7),rs.getInt(8));
                newBug.setDate_created(instanceProjectDAO.getDate(rs.getString(5)));
                newBug.setRequest_id(rs.getInt(9));
                allBugs.add(newBug);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allBugs;
    }
    public ArrayList<Bug> getBugReportsForProject(int idProject){
        ArrayList<Bug> allBugs = new ArrayList<>();
        try{
            getBugReguest.setInt(1,idProject);
            ResultSet rs = getBugReguest.executeQuery();
            while (rs.next()){
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),instanceProjectDAO.findProject("",rs.getInt(6)),rs.getString(7),rs.getInt(8));
                newBug.setDate_created(instanceProjectDAO.getDate(rs.getString(5)));
                newBug.setRequest_id(rs.getInt(9));
                allBugs.add(newBug);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return allBugs;
    }

    public void approveRequestForSolving(int idSolver, String bugName, int bugProjectID){
        try{
            approveRequest.setInt(1,idSolver);
            approveRequest.setString(2,bugName);
            approveRequest.setInt(3,bugProjectID);
            approveRequest.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void denyRequestForSolving( String bugName, int bugProjectID){
        try{
            denyRequest.setString(1,bugName);
            denyRequest.setInt(2,bugProjectID);
            denyRequest.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
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