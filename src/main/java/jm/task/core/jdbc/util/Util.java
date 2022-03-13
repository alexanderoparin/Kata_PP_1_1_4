package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/katabd1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1234!";

    public static Connection connection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        if (!connection.isClosed()) {
            System.out.println("Соединение с БД установлено");
        }
        return connection;
    }
}