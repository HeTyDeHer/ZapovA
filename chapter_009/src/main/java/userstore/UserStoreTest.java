package userstore;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserStoreTest implements UserStore  {

    private SqlSession session;
    private final static UserStoreTest instance = new UserStoreTest();

    public static UserStoreTest getInstance() {
        return instance;
    }

    private UserStoreTest() {
        this.init();
    }

    private void init() {
        try (Reader reader = Resources.getResourceAsReader("configuration.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addUser(User user) {
        session.insert("UserStoreTest.insert", user);
        session.commit();
    }

    public synchronized List<User> getAll() {
        return session.selectList("UserStoreTest.getAll");

    }

    public synchronized User getByLogin(String login) {
        return (User) session.selectOne("UserStoreTest.getByLogin", login);

    }

    public synchronized void update (User user) {
        session.update("UserStoreTest.update", user);
    }


    public synchronized void delete (String login) {
        session.delete("deleteByLogin", login);
        session.commit();
    }

    public void closeSession() {
        session.close();
    }
}
