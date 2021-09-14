package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.createUsersTable();

        service.saveUser("Adam", "Smith", (byte) 31);
        service.saveUser("John", "Keynes", (byte) 45);
        service.saveUser("Maria", "Curie", (byte) 27);
        service.saveUser("Yuri", "Gagarin", (byte) 61);

        for (User user : service.getAllUsers())
            System.out.println(user);

        service.cleanUsersTable();
        service.dropUsersTable();

    }
}
