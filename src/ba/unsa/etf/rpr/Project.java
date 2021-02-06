package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private String name;
    private String description;
    private LocalDate dateProjectCreated;
    private Developer creator;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Project(String name, String description, Developer creator) {
        this.name = name;
        this.description = description;
        this.creator = creator;
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
}
