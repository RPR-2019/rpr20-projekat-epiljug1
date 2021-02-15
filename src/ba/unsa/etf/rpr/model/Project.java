package ba.unsa.etf.rpr.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private String name;
    private String description;
    private LocalDate dateProjectCreated;
    private Developer creator;
    private String client_name;
    private String client_email;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

    public Project(String name, String description, Developer creator, String client_name, String client_email) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.client_name = client_name;
        this.client_email = client_email;
        dateProjectCreated = LocalDate.now();
    }

    public Project(String name, String description, Developer creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        client_name="";
        client_email="";
        dateProjectCreated = LocalDate.now();
    }

    public Developer getCreator() {
        return creator;
    }

    public void setCreator(Developer creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateProjectCreated() {
        return dateProjectCreated.format(myFormatObj);
    }

    public void setDateProjectCreated(LocalDate date_created) {
        this.dateProjectCreated = date_created;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }
}
