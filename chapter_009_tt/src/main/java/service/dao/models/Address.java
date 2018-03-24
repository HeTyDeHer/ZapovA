package service.dao.models;

import java.util.Objects;

public class Address {

    private int id;
    private String country;
    private String city;
    private String street;
    private int home;
    private int apart;
    private User user;

    public Address() {
    }

    public Address(String country, String city, String street, int home, int apart, User user) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.home = home;
        this.apart = apart;
        this.user = user;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getApart() {
        return apart;
    }

    public void setApart(int apart) {
        this.apart = apart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Address address = (Address) o;
        return getId() == address.getId() &&
                getHome() == address.getHome() &&
                getApart() == address.getApart() &&
                Objects.equals(getCountry(), address.getCountry()) &&
                Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getStreet(), address.getStreet()) &&
                Objects.equals(getUser(), address.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCountry(), getCity(), getStreet(), getHome(), getApart(), getUser());
    }

    @Override
    public String toString() {
        return String.format("Address id = %d; %nCountry = %s;%n Town = %s;%nAddress = %s; home %d; apart %d", id, country, city, street, home, apart);
    }
}
