package ba.unsa.etf.rpr.database;

import ba.unsa.etf.rpr.model.Bug;

import java.sql.*;
import java.util.ArrayList;

public class BugDAO {

    private  Connection conn;
    private static BugDAO instance = null;
    private ProjectDAO instanceProjectDAO = ProjectDAO.getInstance();
    private DeveloperDAO developerDAO = DeveloperDAO.getInstance();
    public void backToDefaultDatabase() throws SQLException {
        instanceProjectDAO.backToDefaultDataBase();
    }

    private PreparedStatement findId, getAllBugs,findBugByID, addBug, findMax,getAllBugsForProject, getBugReguest, approveRequest,
            denyRequest,getSolvedBugs,editBug, getAssignedBugs, getAssignedBugsDev, addAssign ,addRequestForBug, checkForRequest
            ,removeBugsForDeveloper,getAssignedBugsForDeveloper,removeAssignedBugs;

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
            DeveloperDAO.backToDefault();
            try{
                getAllBugs = conn.prepareStatement("SELECT * FROM bug");
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }

        try{
            findId = conn.prepareStatement("SELECT bug_id FROM bug where bug_name=? and bug_desc=? and bug_type=? and projectID=?");
            findBugByID = conn.prepareStatement("SELECT * FROM bug WHERE bug_id = ?");
            findMax = conn.prepareStatement("SELECT Max(bug_id) from bug");
            addBug = conn.prepareStatement("INSERT into bug values(?,?,?,?,?,?,?,?,?,?)");
            getAllBugsForProject = conn.prepareStatement("SELECT bug.* " +
                    "from bug where bug.projectID=? and bug.solver_id=0 " +
                    "and (select count(*) from bug_assigned where bug_assigned.project_id=bug.projectID and bug_assigned.bug_id=bug.bug_id)=0");
            getBugReguest = conn.prepareStatement("SELECT DISTINCT bug.* from bug, project where project.project_id=? and project.project_id=bug.projectID and bug.solver_id==0 and bug.request_id!=0");
            approveRequest = conn.prepareStatement("UPDATE bug SET status='Fixed/Riješen', solver_id=?, request_id=0 where bug_name=? and projectID=?");
            denyRequest = conn.prepareStatement("UPDATE bug SET request_id=0,status='Active/Aktivan' where bug_name=? and projectID=?");
            getSolvedBugs = conn.prepareStatement("SELECT * FROM bug where projectID=? and solver_id!=0");
            editBug = conn.prepareStatement("UPDATE bug set bug_name=?,bug_desc=?,bug_type=?,status=?,complexity=?,solver_id=? where bug_name=? and projectID=?");
            getAssignedBugs = conn.prepareStatement("SELECT bug.*,bug_assigned.developer_id FROM bug,bug_assigned where bug.solver_id=0 and bug.bug_id=bug_assigned.bug_id and bug_assigned.project_id=?");
            getAssignedBugsDev = conn.prepareStatement("SELECT bug.*,bug_assigned.developer_id FROM bug,bug_assigned where bug.solver_id=0 and bug.bug_id=bug_assigned.bug_id and bug_assigned.project_id=? and bug_assigned.developer_id=?");
            addAssign = conn.prepareStatement("INSERT INTO bug_assigned VALUES(?,?,?)");
            checkForRequest = conn.prepareStatement("SELECT Count(*) FROM bug WHERE bug_id=? and status='Pending/Čekanje'");
            addRequestForBug = conn.prepareStatement("UPDATE bug SET status='Pending/Čekanje',request_id=? where bug_id=?");
            removeBugsForDeveloper= conn.prepareStatement("DELETE FROM bug_assigned where project_id=? and developer_id=?");
            getAssignedBugsForDeveloper = conn.prepareStatement("SELECT bug.bug_name FROM bug,bug_assigned WHERE bug.bug_id=bug_assigned.bug_id AND bug_assigned.project_id=? AND bug_assigned.developer_id=?");
            removeAssignedBugs = conn.prepareStatement("DELETE FROM bug WHERE bug_name=? and projectID=?");
        }catch (SQLException e){
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
            novi = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),instanceProjectDAO.findProject("",rs.getInt(7)),rs.getString(8),rs.getInt(9),rs.getInt(10));
            novi.setDate_created(rs.getString(6));
            return  novi;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return novi;
    }


    public boolean addRequestForBug(int bugID, int developerID){
        try {
            checkForRequest.setInt(1,bugID);
            ResultSet rs = checkForRequest.executeQuery();
            if(rs.getInt(1)!=0)
                return false;

            addRequestForBug.setInt(1,developerID);
            addRequestForBug.setInt(2,bugID);
            addRequestForBug.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return true;
    }


    public void addNewBug(Bug newBug){
        try{
            addBug.setInt(1,maxIndex());
            addBug.setString(2,newBug.getBug_name());
            addBug.setString(3,newBug.getBug_desc());
            addBug.setString(4,newBug.getBug_type());
            addBug.setString(5,newBug.getStatus());
            addBug.setString(6,newBug.getDate_created());
            addBug.setInt(7,instanceProjectDAO.findID(newBug.getProject()));
            addBug.setString(8,newBug.getComplexity());
            addBug.setInt(9,newBug.getSolver_id());
            addBug.setInt(10,newBug.getRequest_id());
            addBug.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    public int findId(Bug bug){
        try{
            findId.setString(1,bug.getBug_name());
            findId.setString(2,bug.getBug_desc());
            findId.setString(3,bug.getBug_type());
            findId.setInt(4,instanceProjectDAO.findID(bug.getProject()));
            ResultSet rs = findId.executeQuery();
            if(rs.next()) return rs.getInt(1);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return 0;
    }
    public void addAssign(int projectID, int bugID, int developerID){
        try {
            addAssign.setInt(1,projectID);
            addAssign.setInt(2,bugID);
            addAssign.setInt(3,developerID);
            addAssign.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }


    public ArrayList<Bug> getAllSolvedBugsForProject(int id){
        ArrayList<Bug> allBugs = new ArrayList<>();
        try{
            getSolvedBugs.setInt(1,id);
            ResultSet rs = getSolvedBugs.executeQuery();
            while (rs.next()){
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),instanceProjectDAO.findProject("",rs.getInt(7)),rs.getString(8),rs.getInt(9),rs.getInt(10));
                newBug.setDate_created(rs.getString(6));
                allBugs.add(newBug);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allBugs;
    }

    public void editBug(Bug bug, String name, int id){
        try {
            editBug.setString(1,bug.getBug_name());
            editBug.setString(2,bug.getBug_desc());
            editBug.setString(3, bug.getBug_type());
            editBug.setString(4,bug.getStatus());
            editBug.setString(5,bug.getComplexity());
            editBug.setInt(6,bug.getSolver_id());
            editBug.setString(7,name);
            editBug.setInt(8,id);
            editBug.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public ArrayList<Bug> getAllAssignedBugs(int idProject, int idDeveloper){
        ArrayList<Bug> allBugs = new ArrayList<>();
        try {
            ResultSet rs;
            if(idDeveloper==0) {
                getAssignedBugs.setInt(1, idProject);
                rs = getAssignedBugs.executeQuery();
            }else{
                getAssignedBugsDev.setInt(1,idProject);
                getAssignedBugsDev.setInt(2,idDeveloper);
                rs = getAssignedBugsDev.executeQuery();
            }
            while (rs.next()){
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),instanceProjectDAO.findProject("",rs.getInt(7)),rs.getString(8),rs.getInt(9),rs.getInt(10));
                newBug.setDate_created(rs.getString(6));
                newBug.setAssigned(developerDAO.findDeveloperByIDorUsername(rs.getInt(11),""));
                allBugs.add(newBug);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allBugs;
    }
    public ArrayList<Bug> getAllBugsForProject(int id){
        ArrayList<Bug> allBugs = new ArrayList<>();
        try{
            getAllBugsForProject.setInt(1,id);
            ResultSet rs = getAllBugsForProject.executeQuery();
            while (rs.next()){
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),instanceProjectDAO.findProject("",rs.getInt(7)),rs.getString(8),rs.getInt(9),rs.getInt(10));
                newBug.setDate_created(rs.getString(6));
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
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),instanceProjectDAO.findProject("",rs.getInt(7)),rs.getString(8),rs.getInt(9),rs.getInt(10));
                newBug.setDate_created(rs.getString(6));
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
                Bug newBug = new Bug(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),instanceProjectDAO.findProject("",rs.getInt(7)),rs.getString(8),rs.getInt(9),rs.getInt(10));
                newBug.setDate_created(rs.getString(6));
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

    public void removeBugsForRemovedDeveloper(int projectID, int developerID){
        try{
            getAssignedBugsForDeveloper.setInt(1,projectID);
            getAssignedBugsForDeveloper.setInt(2,developerID);
            ResultSet rs = getAssignedBugsForDeveloper.executeQuery();
            while (rs.next()){
                removeAssignedBugs.setString(1,rs.getString(1));
                removeAssignedBugs.setInt(2,projectID);
                removeAssignedBugs.executeUpdate();
            }
            removeBugsForDeveloper.setInt(1,projectID);
            removeBugsForDeveloper.setInt(2,developerID);
            removeBugsForDeveloper.executeUpdate();
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
