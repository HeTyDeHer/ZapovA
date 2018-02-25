package common;

import java.util.List;

public interface UserStore {
     void addUser(User user);
     List<User> getAll();
     User getByLogin(String login);
     void update(User user);
     void delete(String login);
     void closeSession();
}