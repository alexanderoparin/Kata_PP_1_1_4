package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Tom", "Sawyer", (byte) 13);
        userService.saveUser("Huck", "Finn", (byte) 12);
        userService.saveUser("Peter", "Pan", (byte) 15);
        userService.saveUser("Jack", "Smith", (byte) 14);

        System.out.println(userService.getAllUsers().toString());

        userService.removeUserById(2);

        userService.dropUsersTable();
    }
}
