package common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserStoreTest implements UserStore {


    private SqlSessionFactory sqlSessionFactory;
    private static final UserStoreTest INSTANCE = new UserStoreTest();

    public static UserStoreTest getInstance() {
        return INSTANCE;
    }

    private UserStoreTest() {
        this.init();
    }

    private void init() {
        try (Reader reader = Resources.getResourceAsReader("configuration.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("UserStoreTest.insert", user);
            session.commit();
        }
    }

    public List<User> getAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("UserStoreTest.getAll");
        }
    }

    public User getByLogin(String login) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return (User) session.selectOne("UserStoreTest.getByLogin", login);
        }

    }

    public synchronized void update(User user) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("UserStoreTest.update", user);
            session.commit();
        }
    }


    public void delete(String login) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("deleteByLogin", login);
            session.commit();
        }
    }
}
