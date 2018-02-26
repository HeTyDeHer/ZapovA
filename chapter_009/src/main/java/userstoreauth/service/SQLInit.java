package userstoreauth.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * 1. Реализовать авторизация и аутентификацию [#2517].
 * DB Connection class.
 * initTables() check if all tables exist and create admin account, if none (admin/admin).
 * getSession() returns SqlSession from sqlSessionFactory.
 * Singleton. Package-private access.
 */
public class SQLInit {

    private static final Logger logger = LoggerFactory.getLogger(SQLInit.class);

    private SqlSessionFactory sqlSessionFactory;

    private static final SQLInit INSTANCE = new SQLInit();

    static SQLInit getInstance() {
        return INSTANCE;
    }

    private SQLInit() {
        this.initFactory();
        this.initTables();
    }

    private void initFactory() {
        try (Reader reader = Resources.getResourceAsReader("configurationVer2.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initTables() {
        try(SqlSession session = this.getSession()) {
            session.insert("initTables.createMainTable");
            session.insert("initTables.createAuthorizationTable");
            session.insert("initTables.createSessionsTable");
            if (session.selectOne("UserStoreTestVer2.getByLogin", "admin") == null) {
                session.insert("initTables.createAdmin");
            }
            session.commit();
        }
    }

    SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }



}
