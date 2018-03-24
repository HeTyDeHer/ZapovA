package service.rep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.models.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitiesRep {

    private DBConnectionPool db = DBConnectionPool.getDb();
    private static final Logger logger = LoggerFactory.getLogger(CitiesRep.class);

    public List<String> readAllCountries() {
        List<String> result = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(CITIES.READ_COUNTRY.toString())) {
            while(rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public List<City> readAllCitiesOfCOuntry(String country) {
        List<City> result = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(CITIES.READ_CITY.toString())) {
            ps.setString(1, country);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    City city = new City();
                    city.setId(rs.getInt("id"));
                    city.setName(rs.getString("city"));
                    city.setCountry(rs.getString("country"));
                    result.add(city);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public int getCityId(String country, String city) {
        int result = -1;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(CITIES.READ_ID.toString())) {
            ps.setString(1, country);
            ps.setString(2, city);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    result = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private enum CITIES {
        READ_CITY("SELECT * FROM cities WHERE country = ? ORDER BY city"),
        READ_COUNTRY("SELECT DISTINCT country FROM cities ORDER BY country"),
        READ_ID("SELECT id FROM cities WHERE country = ? AND city = ? ");
        private final String text;

        CITIES(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
