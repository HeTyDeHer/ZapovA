package common;

import java.sql.Timestamp;

public class User {

    private String login;
    private String name;
    private String email;
    private Timestamp created;

    public User() {
    }

    public User(String login, String name, String email, Timestamp created) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.created = created;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                '}';
    }
}
