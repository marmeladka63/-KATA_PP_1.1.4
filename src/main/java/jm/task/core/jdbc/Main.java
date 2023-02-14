package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();


        userService.saveUser("Иван", "Петров", (byte) 37);
        userService.saveUser("Лев", "Толстой", (byte) 23);
        userService.saveUser("Ильф", "Петров", (byte) 18);
        userService.saveUser("Александр", "Пушкин", (byte) 29);
        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}

