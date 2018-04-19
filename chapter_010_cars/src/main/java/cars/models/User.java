package cars.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer_owner")
    private Set<CarOffer> offers = new HashSet<>();

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<CarOffer> getOffers() {
        return offers;
    }

    public void setOffers(Set<CarOffer> offers) {
        this.offers = offers;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLogin(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
//                ", offers=" + offers +
                '}';
    }
}
