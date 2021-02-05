package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ProjectDAO {
    private static ProjectDAO instance = null;
    private static Connection conn;
    private PreparedStatement getAllProjects,findProject, addProject, findMax;

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
            findProject = conn.prepareStatement("SELECT * FROM project where naziv=?");
            addProject = conn.prepareStatement("INSERT INTO developer values(?,?,?,?,?)");
            findMax = conn.prepareStatement("SELECT Max(developer_id) from developer");
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
}
