package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45), lastName VARCHAR(45), age TINYINT(100));";
        try {
            Util.connection().createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица users создана");
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users;";
        try {
            Util.connection().createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица users удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users(name, lastName, age) VALUES('" + name + "', '" + lastName + "', '" + age + "');";
        try {
            Util.connection().createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем " + name + "_" + lastName + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = " + id + ";";
        try {
            Util.connection().createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с id " + id + " удалён из таблицы");
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String query = "SELECT * FROM users;";
        ResultSet res = null;
        try {
            res = Util.connection().createStatement().executeQuery(query);
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong(1));
                user.setName(res.getString(2));
                user.setLastName(res.getString(3));
                user.setAge(res.getByte(4));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Лист из Users создан");
        return allUsers;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM users;";
        try {
            Util.connection().createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица users очищена");
    }
}
