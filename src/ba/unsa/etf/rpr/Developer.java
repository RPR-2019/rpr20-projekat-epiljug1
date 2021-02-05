package ba.unsa.etf.rpr;

public class Developer {
    private String ime;
    private String prezime;
    private String email;
    private String username;
    private String password;


    public Developer(String ime, String prezime, String email, String username, String password) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
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
}
