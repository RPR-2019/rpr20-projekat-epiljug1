package ba.unsa.etf.rpr.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bug {
    private String bug_name;
    private String bug_desc;
    private String bug_type;
    private String status;
    private String date_created;
    private Project project;
    private String complexity;
    private Developer assigned;
    private int solver_id;
    private int request_id;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public Bug(String bug_name,String bug_desc ,String bug_type, String status, Project project, String complexity, int id_solver, int id_request) {
        this.bug_name = bug_name;
        this.bug_desc = bug_desc;
        this.bug_type = bug_type;
        this.status = status;
        this.date_created = LocalDate.now().format(myFormatObj);
        this.project = project;
        this.complexity = complexity;
        solver_id = id_solver;
        request_id = id_request;
    }
    public Bug(String bug_name,String bug_desc, String bug_type, String status, Project project, String complexity, int id_solver) {
        this.bug_name = bug_name;
        this.bug_type = bug_type;
        this.bug_desc = bug_desc;
        this.status = status;
        this.date_created = LocalDate.now().format(myFormatObj);
        this.project = project;
        this.complexity = complexity;
        solver_id = id_solver;
        request_id = 0;
    }
    public Bug(String bug_name,String bug_desc, String bug_type, String status, Project project, String complexity) {
        this.bug_name = bug_name;
        this.bug_type = bug_type;
        this.bug_desc = bug_desc;
        this.status = status;
        this.date_created = LocalDate.now().format(myFormatObj);
        this.project = project;
        this.complexity = complexity;
        solver_id = 0;
        request_id = 0;
    }

    public Developer getAssigned() {
        return assigned;
    }

    public void setAssigned(Developer assigned) {
        this.assigned = assigned;
    }

    public String getBug_desc() {
        return bug_desc;
    }

    public void setBug_desc(String bug_desc) {
        this.bug_desc = bug_desc;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getSolver_id() {
        return solver_id;
    }

    public void setSolver_id(int solver_id) {
        this.solver_id = solver_id;
    }

    public String getBug_name() {
        return bug_name;
    }

    public void setBug_name(String bug_name) {
        this.bug_name = bug_name;
    }

    public String getBug_type() {
        return bug_type;
    }

    public void setBug_type(String bug_type) {
        this.bug_type = bug_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDate_created() {
        return  date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    @Override
    public boolean equals(Object obj) {
        Bug novi = (Bug) obj;
        return getBug_name().equals(novi.getBug_name()) &&  getBug_type().equals(novi.getBug_type()) && getDate_created().equals(novi.getDate_created())
                && getProject().getName().equals(novi.getProject().getName());
    }
}
