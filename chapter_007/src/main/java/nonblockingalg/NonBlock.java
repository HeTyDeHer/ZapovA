package nonblockingalg;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. Неблокирующий кеш [#4741].
 * Created by Алексей on 10.12.2017.
 */
public class NonBlock {

    private final ConcurrentHashMap<Integer, Model> models = new ConcurrentHashMap<>();

    public boolean add(Model model) {
        return models.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update (Model model) {
        return models.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new RuntimeException();
            }
            value = model;
            value.increaseVersion();
            return value;
        }) == null;
    }

    public boolean delete(Model model) {
        return models.remove(model.getId()) != null;
    }
}

class Model {
    private int id;
    private String data;
    private int version;

    public Model(int id) {
        this.id = id;
        this.version = 0;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void increaseVersion() {
        this.version++;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        return getId() == model.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", version=" + version +
                '}';
    }
}
