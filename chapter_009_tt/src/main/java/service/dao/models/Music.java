package service.dao.models;

import java.util.Objects;

public class Music {
    private int id;
    private String genre;

    public Music() {
    }

    public Music(int id, String genre) {

        this.id = id;
        this.genre = genre;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return id == music.id &&
                Objects.equals(genre, music.genre);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, genre);
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                '}';
    }
}
