package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserDao repository = new UserDaoJDBCImpl();

    public void createUsersTable() {
        repository.createUsersTable();
    }

    public void dropUsersTable() {
        repository.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        repository.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        repository.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public void cleanUsersTable() {
        repository.cleanUsersTable();
    }
}
