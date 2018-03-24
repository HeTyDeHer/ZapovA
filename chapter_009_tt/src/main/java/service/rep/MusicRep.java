package service.rep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.MusicDaoImpl;
import service.dao.models.Music;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MusicRep extends MusicDaoImpl {
    private DBConnectionPool db = DBConnectionPool.getDb();
    private static final Logger logger = LoggerFactory.getLogger(MusicRep.class);

    public List<Music> readAll() {
        List<Music> result = new ArrayList<>();
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(MUSIC.READ_MUS.toString())) {
            while (rs.next()) {
                Music music = new Music();
                music.setId(rs.getInt("id"));
                music.setGenre(rs.getString("genre"));
                result.add(music);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private enum MUSIC {
        READ_MUS("SELECT * FROM music");
        private final String text;

        MUSIC(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

}
