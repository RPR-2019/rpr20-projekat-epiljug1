package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectDAO {
    private static ProjectDAO instance = null;
    private static DeveloperDAO instanceDeveloper = DeveloperDAO.getInstance();
    private static Connection conn;
    private PreparedStatement getAllProjects,findProject, addProject, findMax,findId,addProjectConnectionTable;

    public static Connection getConn() {
        return conn;
    }

    public static ProjectDAO getInstance() {
        if(instance==null) instance = new ProjectDAO();
        return instance;
    }

    private ProjectDAO(){
        try{
            //connecting with database
            conn = DriverManager.getConnection("jdbc:sqlite:BugTracker.db");
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            getAllProjects = conn.prepareStatement("SELECT * FROM project");
        }catch (SQLException e){
            createDataBase();
            try{
                getAllProjects = conn.prepareStatement("SELECT * FROM project");
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }

        try{
            findProject = conn.prepareStatement("SELECT * FROM project where naziv=? or project_id=?");
            addProject = conn.prepareStatement("INSERT INTO project values(?,?,?,?,?)");
            findMax = conn.prepareStatement("SELECT Max(project_id) from project");
            findId = conn.prepareStatement("SELECT project_id FROM project where   naziv=? and opis=? ");
            addProjectConnectionTable = conn.prepareStatement("INSERT INTO connections values(?,?)");
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

    public    LocalDate getDate(String date){
        int day=0, month=0, year=0, i=0;
        for(String x : date.split(".")){
            if(i==0) day = Integer.parseInt(x);
            if(i==1) month = Integer.parseInt(x);
            if(i==2) year = Integer.parseInt(x);
            i++;
        }
        return LocalDate.of(year,month,day);
    }

    public Project findProject(String naziv, int id){
        Project novi = null;
        try{
            findProject.setString(1,naziv);
            findProject.setInt(2,id);
            ResultSet rs = findProject.executeQuery();
            novi = new Project(rs.getString(2),rs.getString(3), instanceDeveloper.findDeveloperByID(rs.getInt(4)));
            novi.setDateProjectCreated(getDate(rs.getString(5)));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  novi;
    }

    public void addNewProject(Project project){
        try{
            int id = maxIndex();
            int idDevelopera = instanceDeveloper.findIdOfDeveloper(project.getCreator().getUsername());
            addProject.setInt(1,id);
            addProject.setString(2,project.getName());
            addProject.setString(3,project.getDescription());
            addProject.setInt(4,idDevelopera);

            addProjectConnectionTable.setInt(1,id);
            addProjectConnectionTable.setInt(2,idDevelopera);

            addProjectConnectionTable.executeUpdate();
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
                Project novi = new Project(rs.getString(2),rs.getString(3), instanceDeveloper.findDeveloperByID(rs.getInt(4)));
                novi.setDateProjectCreated(getDate(rs.getString(5)));
                allProjects.add(novi);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allProjects;
    }

    int findID(Project project){
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
}
