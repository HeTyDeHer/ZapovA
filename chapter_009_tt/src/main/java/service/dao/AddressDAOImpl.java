package service.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.models.Address;

import java.sql.*;

public class AddressDAOImpl implements AddressDAO {

    private static final Logger logger = LoggerFactory.getLogger(AddressDAOImpl.class);
    private final DBConnectionPool db = DBConnectionPool.getDb();

    @Override
    public int create(Address model) {
        int key = -1;
        if (model == null) {
            return key;
        }
        try (Connection connection = db.getConnection();
             PreparedStatement getCityId = connection.prepareStatement(ADDR.READ_CITY_ID.toString())) {
            getCityId.setString(1, model.getCountry());
            getCityId.setString(2, model.getCity());
            try (ResultSet cityId = getCityId.executeQuery();
                 PreparedStatement ps = connection.prepareStatement(ADDR.ADD_ADDR.toString(), Statement.RETURN_GENERATED_KEYS)) {
                if (cityId.next()) {
                    ps.setInt(1, cityId.getInt(1));
                    ps.setString(2, model.getStreet());
                    ps.setInt(3, model.getHome());
                    ps.setInt(4, model.getApart());
                }
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    key = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return key;
    }

    @Override
    public Address read(Integer key) {
        Address address = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(ADDR.READ_ADDR.toString())) {
            ps.setInt(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    address = new Address();
                    address.setId(rs.getInt("id"));
                    address.setCountry(rs.getString("country"));
                    address.setCity(rs.getString("city"));
                    address.setStreet(rs.getString("street"));
                    address.setHome(rs.getInt("home"));
                    address.setApart(rs.getInt("apart"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return address;
    }

    @Override
    public boolean update(Address model) {
        boolean result = false;
        if (model != null) {
            try (Connection connection = db.getConnection();
                 PreparedStatement city_id = connection.prepareStatement(ADDR.READ_CITY_ID.toString());
                 PreparedStatement ps = connection.prepareStatement(ADDR.UPDATE_ADDR.toString())) {
                city_id.setString(1, model.getCountry());
                city_id.setString(2, model.getCity());
                try (ResultSet rs = city_id.executeQuery()) {
                    if (rs.next()) {
                        ps.setInt(1, rs.getInt(1));
                        ps.setString(2, model.getStreet());
                        ps.setInt(3, model.getHome());
                        ps.setInt(4, model.getApart());
                        ps.setInt(5, model.getId());
                        ps.execute();
                        result = true;
                    }
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public void delete(int id) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(ADDR.DELETE_ADDR.toString())) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Address readAddressModel(int city_id, String street, int home, int apart) {
        Address address = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(ADDR.GET_ADDR.toString())) {
            ps.setInt(1, city_id);
            ps.setString(2, street);
            ps.setInt(3, home);
            ps.setInt(4, apart);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    address = new Address();
                    address.setId(rs.getInt("id"));
                    address.setCountry(rs.getString("country"));
                    address.setCity(rs.getString("city"));
                    address.setStreet(rs.getString("street"));
                    address.setHome(rs.getInt("home"));
                    address.setApart(rs.getInt("apart"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return address;
    }

    private enum ADDR {
        ADD_ADDR("INSERT INTO address (city_id, street, home, apart) VALUES (?, ?, ?, ?)"),
        DELETE_ADDR("DELETE FROM address WHERE id = ?"),
        GET_ADDR("SELECT address.id, country, city, street, home, apart FROM address" +
                "                INNER JOIN cities ON cities.id = address.city_id WHERE cities.id = ? AND street = ? AND home = ? AND apart = ?"),
        READ_CITY_ID("SELECT id FROM cities WHERE country = ? AND city = ?"),
        READ_ADDR("SELECT address.id, country, city, street, home, apart FROM address " +
                "INNER JOIN cities ON cities.id = address.city_id WHERE address.id = ?"),
        UPDATE_ADDR("UPDATE address SET city_id = ?, street = ?, home = ?, apart = ? WHERE id = ?");


        private final String text;

        ADDR(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
