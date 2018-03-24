package service.dao;

import service.dao.models.User;

public interface UserDAO extends AbstractDAO<User, Integer> {
    User readByLogin(String key);
}
