package userstoreauth.service;

import org.apache.ibatis.session.SqlSession;
import userstoreauth.model.UserVer2;

import java.util.HashMap;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * Remember user by cookies.
 * Creates/deletes pair user.login - cookies.id in database.
 */
public class ManageSessions {

    private static final SQLInit sqlConnection = SQLInit.getInstance();

    public void deleteSessions(String login) {
        try (SqlSession session = sqlConnection.getSession()) {
            session.delete("manageSessions.deleteLoginSessions", login);
            session.commit();
        }
    }

    public void rememberSession(String id, UserVer2 user) {
        try (SqlSession session = sqlConnection.getSession()) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("id", id);
            hm.put("login", user.getLogin());
            session.insert("manageSessions.rememberSession", hm);
            session.commit();
        }
    }

    public UserVer2 getUserBySessionID(String uuid) {
        try (SqlSession session = sqlConnection.getSession()) {
            return (UserVer2) session.selectOne("manageSessions.getUserIdByUUID", uuid);
        }
    }
}

