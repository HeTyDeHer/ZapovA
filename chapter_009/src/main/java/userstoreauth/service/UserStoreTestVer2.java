package userstoreauth.service;

import org.apache.ibatis.session.SqlSession;
import userstoreauth.model.UserVer2;

import java.util.List;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * All CRUD User (model.UserVer2) operations.
 */
public class UserStoreTestVer2 {

    private final static SQLInit sqlConnection = SQLInit.getInstance();

    public void addUser(UserVer2 user) {
        try (SqlSession session = sqlConnection.getSession()) {
            session.insert("UserStoreTestVer2.insert", user);
            session.commit();
        }
    }

    public List<UserVer2> getAll() {
        try (SqlSession session = sqlConnection.getSession()) {
            return session.selectList("UserStoreTestVer2.getAll");
        }
    }

    public UserVer2 getByLogin(String login) {
        try (SqlSession session = sqlConnection.getSession()) {
            return (UserVer2) session.selectOne("UserStoreTestVer2.getByLogin", login);
        }

    }

    public void update(UserVer2 user) {
        try (SqlSession session = sqlConnection.getSession()) {
            session.update("UserStoreTestVer2.update", user);
            session.commit();
        }
    }

    public void delete(String login) {
        try (SqlSession session = sqlConnection.getSession()) {
            session.delete("UserStoreTestVer2.deleteByLogin", login);
            session.commit();
        }
    }

    public void deleteAll() {
        try (SqlSession session = sqlConnection.getSession()) {
            session.delete("UserStoreTestVer2.deleteAll");
            session.commit();
        }
    }

}
