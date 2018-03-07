package userstoreauth.model;

import java.sql.Timestamp;
import java.util.Objects;

public class UserVer2 {

    private String login;
    private String password;
    private String name;
    private String email;
    private String country;
    private String city;
    private Timestamp created;
    private String role;

    public UserVer2() {
    }

    public UserVer2(String login, String password, String name, String email, String country, String city, Timestamp created, String role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.country = country;
        this.city = city;
        this.created = created;
        this.role = role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {

        this.country = country;
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

    public String getCity() {
        return city;
    }

    public String getCountry() {

        return country;
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
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", created=" + created +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVer2 userVer2 = (UserVer2) o;
        return Objects.equals(getLogin(), userVer2.getLogin()) &&
                Objects.equals(getPassword(), userVer2.getPassword()) &&
                Objects.equals(getName(), userVer2.getName()) &&
                Objects.equals(getEmail(), userVer2.getEmail()) &&
                Objects.equals(getRole(), userVer2.getRole());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLogin(), getPassword(), getName(), getEmail(), getRole());
    }
}
