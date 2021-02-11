package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bug {
    private String bug_name;
    private String bug_type;
    private String status;
    private LocalDate date_created;
    private Project project;
    private String complexity;
    private int solver_id;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

    public Bug(String bug_name, String bug_type, String status, Project project, String complexity,int id) {
        this.bug_name = bug_name;
        this.bug_type = bug_type;
        this.status = status;
        this.date_created = LocalDate.now();
        this.project = project;
        this.complexity = complexity;
        this.solver_id = id;
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
        return date_created.format(myFormatObj);
    }

    public void setDate_created(LocalDate date_created) {
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
}
