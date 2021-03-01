package ba.unsa.etf.rpr.database;

import ba.unsa.etf.rpr.model.Developer;
import ba.unsa.etf.rpr.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class ProjectDAO {
    private static ProjectDAO instance = null;
    private static DeveloperDAO instanceDeveloper;

    public void backToDefaultDataBase() throws SQLException {
        instanceDeveloper.backToDefaultDatabase();
    }

    private  Connection conn;
    private PreparedStatement getAllProjects,findProject, addProject, findMax,findId,addProjectConnectionTable,
            getAllProjectsOfDeveloper, getAllProjectsUserIsAssigned, updateProject, countBugs, countSolvedBugs
            , getAllDevelopersWhoWorksOnProject, searchForDeveloper, addDeveloperOnProject, removeDeveloper;

    public  Connection getConn() {
        return conn;
    }

    public static ProjectDAO getInstance() {
        if(instance==null) instance = new ProjectDAO();
        return instance;
    }

    private ProjectDAO(){
        instanceDeveloper = DeveloperDAO.getInstance();
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
            getAllProjects = conn.prepareStatement("SELECT * FROM project");
        }catch (SQLException e){
            DeveloperDAO.backToDefault();
            try{
                getAllProjects = conn.prepareStatement("SELECT * FROM project");
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }

        try{
            updateProject = conn.prepareStatement("update project set naziv=?, opis=?, client_name=?,client_email=?, code_link=? where project_id=?");
            getAllProjectsOfDeveloper= conn.prepareStatement("select * from project where creator_id=?");
            findProject = conn.prepareStatement("SELECT * FROM project where naziv=? or project_id=?");
            addProject = conn.prepareStatement("INSERT INTO project values(?,?,?,?,?,?,?,?)");
            findMax = conn.prepareStatement("SELECT Max(project_id) from project");
            findId = conn.prepareStatement("SELECT project_id FROM project where   naziv=? and opis=? ");
            addProjectConnectionTable = conn.prepareStatement("INSERT INTO connections values(?,?)");
            getAllProjectsUserIsAssigned = conn.prepareStatement("select project.* from project,connections where connections.pr_id=project.project_id and connections.de_id=?");
            countBugs = conn.prepareStatement("select count(*) from project a,bug b where a.project_id=b.projectID and a.project_id=?");
            countSolvedBugs = conn.prepareStatement("select count(*) from project a, bug b where a.project_id=b.projectID  and a.project_id=? and b.solver_id is not null and b.solver_id!=0");
            getAllDevelopersWhoWorksOnProject
                    = conn.prepareStatement("SELECT DISTINCT developer.* from developer, project, connections where project.project_id=? and project.project_id=connections.pr_id and developer.developer_id=connections.de_id");
            searchForDeveloper
                    = conn.prepareStatement("SELECT developer.* from developer where developer.username!=? and (LOWER(developer.ime)=LOWER(?) or LOWER(developer.prezime)=LOWER(?) or LOWER(developer.username)=LOWER(?) or LOWER(developer.email)=LOWER(?))");

            addDeveloperOnProject = conn.prepareStatement("INSERT into connections VALUES(?,?)");
            removeDeveloper = conn.prepareStatement("DELETE FROM connections where connections.pr_id=? and connections.de_id=?");
            //MINUS SELECT DISTINCT developer.* from developer, project, connections where project.project_id=? and project.project_id=connections.pr_id and developer.developer_id=connections.de_id
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


    public  LocalDate getDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return LocalDate.parse(date, formatter);
    }

    public Project findProject(String naziv, int id){
        Project novi = null;
        try{
            findProject.setString(1,naziv);
            findProject.setInt(2,id);
            ResultSet rs = findProject.executeQuery();
            novi = new Project(rs.getString(2),rs.getString(3), instanceDeveloper.findDeveloperByIDorUsername(rs.getInt(4),""),rs.getString(6),rs.getString(7),rs.getString(8));
            novi.setDateProjectCreated(rs.getString(5));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return  novi;
    }

    public void addNewProject(Project project){
        try{
            int id = maxIndex();

            addProject.setInt(1,id);
            addProject.setString(2,project.getName());
            addProject.setString(3,project.getDescription());
            addProject.setInt(4,instanceDeveloper.findIdOfDeveloper(project.getCreator().getUsername()));
            addProject.setString(5,project.getDateProjectCreated());
            addProject.setString(6,project.getClient_name());
            addProject.setString(7,project.getClient_email());
            addProject.setString(8,project.getCode_link());

            addProject.executeUpdate();


        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public ArrayList<Project> getAllProjects(){
        ArrayList<Project> allProjects =  new ArrayList<>();
        try {
            ResultSet rs = getAllProjects.executeQuery();
            while (rs.next()){
                Project novi = new Project(rs.getString(2),rs.getString(3), instanceDeveloper.findDeveloperByIDorUsername(rs.getInt(4),""),rs.getString(6),rs.getString(7),rs.getString(8));
                novi.setDateProjectCreated(rs.getString(5));
                allProjects.add(novi);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allProjects;
    }

    public ArrayList<Project> getAllProjectsForDeveloper(Developer developer){
        int id = instanceDeveloper.findIdOfDeveloper(developer.getUsername());
        ArrayList<Project> allProjects = new ArrayList<>();
        try{
            getAllProjectsUserIsAssigned.setInt(1,id);
            ResultSet rs = getAllProjectsUserIsAssigned.executeQuery();
            while (rs.next()){
                Project novi = new Project(rs.getString(2),rs.getString(3), instanceDeveloper.findDeveloperByIDorUsername(rs.getInt(4),""),rs.getString(6),rs.getString(7),rs.getString(8));
                novi.setDateProjectCreated(rs.getString(5));
                allProjects.add(novi);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return allProjects;
    }

    public ArrayList<Project> getAllProjectsOfDeveloper(Developer developer){
        int id = instanceDeveloper.findIdOfDeveloper(developer.getUsername());
        ArrayList<Project> allProjects =  new ArrayList<>();
        try{
            getAllProjectsOfDeveloper.setInt(1,id);
            ResultSet rs = getAllProjectsOfDeveloper.executeQuery();
            while (rs.next()){
                Project novi = new Project(rs.getString(2),rs.getString(3), instanceDeveloper.findDeveloperByIDorUsername(rs.getInt(4),""),rs.getString(6),rs.getString(7),rs.getString(8));
                novi.setDateProjectCreated(rs.getString(5));

                allProjects.add(novi);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allProjects;
    }

    public void updateProject(int id, String name, String description, String client_name, String client_email,String code_link){
        try{
            updateProject.setString(1,name);
            updateProject.setString(2,description);
            updateProject.setString(3,client_name);
            updateProject.setString(4,client_email);
            updateProject.setString(5,code_link);
            updateProject.setInt(6,id);
            updateProject.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    public ObservableList<PieChart.Data> getProjectGraphStatistic(Project project) {
        int id = findID(project);
        System.out.println(">>>ID je " + id);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            countBugs.setInt(1,id);
            countSolvedBugs.setInt(1,id);
            ResultSet rs1= countBugs.executeQuery();
            ResultSet rs2= countSolvedBugs.executeQuery();

            if (rs1.next()) {
                int count = rs1.getInt(1);
                System.out.println("COUNT 1 = " + count);
                if(Locale.getDefault().getCountry().equals("US"))
                    data.add(new PieChart.Data("Total bugs (" + count + ")", count));
                else
                    data.add(new PieChart.Data("Ukupno bug-ova (" + count + ")", count));
            }

            if (rs2.next()) {
                int count = rs2.getInt(1);
                System.out.println("COUNT 2 = " + count);
                if(Locale.getDefault().getCountry().equals("US"))
                    data.add(new PieChart.Data("Solved bugs (" + count + ")", count));
                else
                    data.add(new PieChart.Data("Riješeni bug-ovi (" + count + ")", count));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<Developer> getAllDevelopersWhoWorksOnAProject(int projectID){
        ArrayList<Developer> listDevelopers = new ArrayList<>();
        try{
           // System.out.println("PROJECT ID = "+ projectID);
            getAllDevelopersWhoWorksOnProject.setInt(1,projectID);
            ResultSet rs = getAllDevelopersWhoWorksOnProject.executeQuery();
            while (rs.next()){
             //   System.out.println("DEVELOPER " + rs.getString(2));
                listDevelopers.add(new Developer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return listDevelopers;
    }

    public ArrayList<Developer> searchForDevelopers(String search , String creatorUsername){
        ArrayList<Developer> listDevelopers = new ArrayList<>();
        try{
            searchForDeveloper.setString(1,creatorUsername);
            searchForDeveloper.setString(2,search);
            searchForDeveloper.setString(3,search);
            searchForDeveloper.setString(4,search);
            searchForDeveloper.setString(5,search);
            ResultSet rs = searchForDeveloper.executeQuery();
            while (rs.next())
                listDevelopers.add(new Developer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return listDevelopers;
    }

    public void removeDeveloperFromProject(int projectId, int developerId){
        try {
            removeDeveloper.setInt(1,projectId);
            removeDeveloper.setInt(2,developerId);
            removeDeveloper.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void addDeveloperOnProject(int projectId, int developerId){
        try {
            addDeveloperOnProject.setInt(1,projectId);
            addDeveloperOnProject.setInt(2,developerId);
            addDeveloperOnProject.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }


    public int findID(Project project){
        try {
            findId.setString(1,project.getName());
            findId.setString(2,project.getDescription());
            ResultSet rs = findId.executeQuery();
            if(rs.next()) return  rs.getInt(1);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return 0;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
