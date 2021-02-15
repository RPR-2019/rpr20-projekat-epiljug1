package ba.unsa.etf.rpr.model;

public class Developer {
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;


    public Developer(String ime, String prezime, String email, String username, String password) {
        this.name = ime;
        this.surname = prezime;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getName() +" " + getSurname();
    }
}
