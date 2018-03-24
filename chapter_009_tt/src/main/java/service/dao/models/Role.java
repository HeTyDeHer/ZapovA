package service.dao.models;

import java.util.Objects;

public class Role {
    private int id;
    private String role;
    private String description;


    @Override
    public String toString() {
        return  role +
                ", " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return getId() == role1.getId() &&
                Objects.equals(getRole(), role1.getRole()) &&
                Objects.equals(getDescription(), role1.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getRole(), getDescription());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role(String role, String description) {

        this.role = role;
        this.description = description;
    }

    public Role() {

    }
}
