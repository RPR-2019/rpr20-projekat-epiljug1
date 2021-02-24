package ba.unsa.etf.rpr.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private String name;
    private String description;
    private String dateProjectCreated;
    private Developer creator;
    private String client_name;
    private String client_email;
    private String code_link;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public Project(String name, String description, Developer creator, String client_name, String client_email,String code_link) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.client_name = client_name;
        this.client_email = client_email;
        this.code_link = code_link;
        dateProjectCreated = (LocalDate.now()).format(myFormatObj);
    }

    public Project(String name, String description, Developer creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        client_name="";
        client_email="";
        dateProjectCreated = (LocalDate.now()).format(myFormatObj);
    }

    public String getCode_link() {
        return code_link;
    }

    public void setCode_link(String code_link) {
        this.code_link = code_link;
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
        return dateProjectCreated;
    }

    public void setDateProjectCreated(String date_created) {
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
