package service.dao.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private Address address;
    private Role role;
    private List<Music> music;

    public User() {
        music = new ArrayList<>();
    }

    public User(String login, String password, String name, Address address, Role role, List<Music> music) {

        this.login = login;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
        this.music = music;
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

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
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

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public List<Music> getMusic() {
        return music;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getLogin(), getPassword(), getName());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", role=" + role +
                ", music=" + music +
                '}';
    }
}
