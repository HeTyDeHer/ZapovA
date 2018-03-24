package service.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DBConnectionPool;
import service.dao.models.Music;

import java.sql.*;

public class MusicDaoImpl implements AbstractDAO<Music, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(MusicDaoImpl.class);
    private final DBConnectionPool db = DBConnectionPool.getDb();

    @Override
    public int create(Music model) {
        int key = -1;
        if (model != null) {
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(MUSIC.ADD_MUS.toString(), Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement userMusic = connection.prepareStatement(MUSIC.ADD_USER_MUSIC.toString())) {
                ps.setString(1, model.getGenre());
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    key = rs.getInt("id");
                }

            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return key;

    }

    @Override
    public Music read(Integer key) {
        Music music = null;
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(MUSIC.READ_MUS.toString())) {
            ps.setInt(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    music = new Music();
                    music.setId(rs.getInt("id"));
                    music.setGenre(rs.getString("genre"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return music;
    }

    @Override

    public boolean update(Music model) {
        if (model != null) {
            try (Connection connection = db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(MUSIC.UPDATE_MUS.toString())) {
                ps.setString(1, model.getGenre());
                ps.setInt(2, model.getId());
                ps.execute();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(int id) {
        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(MUSIC.DELETE_MUS.toString())) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private enum MUSIC {
        ADD_MUS("INSERT INTO music (genre) VALUES (?)"),
        ADD_USER_MUSIC("INSERT INTO users_music (user_id, music_id) VALUES (?, ?)"),
        READ_MUS("SELECT * FROM music WHERE id = ?"),
        UPDATE_MUS("UPDATE music SET genre = ? WHERE id = ?"),
        DELETE_MUS("DELETE FROM music WHERE id = ?");

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
