package service.rep;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DBConnectionPool;
import service.dao.models.Music;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MusicRepTest {

    private MusicRep musicDAO = new MusicRep();

    @BeforeEach
    private void setUp() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
    }

    @AfterAll
    private static void close() {
        DBConnectionPool db = DBConnectionPool.getDb();
        db.deleteAll();
        db.close();
    }

    @Test
    void createAndRead() {
        Music music = new Music();
        music.setGenre("Rock");
        Music music2 = new Music();
        music2.setGenre("Pop");
        int id = musicDAO.create(music);
        int id2 = musicDAO.create(music2);
        assertNotEquals(-1, id);
        assertNotEquals(-1, id2);
        music.setId(id);
        music2.setId(id2);
        Music testMusic = musicDAO.read(id);
        assertEquals(music, testMusic);
        List<Music> expected = Arrays.asList(music, music2);
        assertArrayEquals(expected.toArray(), musicDAO.readAll().toArray());
    }

    @Test
    void update() {
        Music music = new Music();
        music.setGenre("Rock");
        int id = musicDAO.create(music);
        assertNotEquals(-1, id);
        music.setId(id);
        music.setGenre("Тест");
        musicDAO.update(music);
        assertEquals(music, musicDAO.read(id));
    }

    @Test
    void delete() {
        Music music = new Music();
        music.setGenre("Rock");
        int id = musicDAO.create(music);
        assertNotEquals(-1, id);
        assertNotNull(musicDAO.read(id));
        musicDAO.delete(id);
        assertNull(musicDAO.read(id));
    }
}