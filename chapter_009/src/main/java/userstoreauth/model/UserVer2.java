package userstoreauth.model;

import java.sql.Timestamp;

public class UserVer2 {

    private String login;
    private String password;
    private String name;
    private String email;
    private Timestamp created;
    private String role;

    public UserVer2() {
    }

    public UserVer2(String login, String password, String name, String email, Timestamp created, String role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.created = created;
        this.role = role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserVer2{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", role='" + role + '\'' +
                '}';
    }
}
