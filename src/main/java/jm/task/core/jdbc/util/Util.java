package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/katabd1?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1234!";

    private static SessionFactory factory;

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry registry = (ServiceRegistry) new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                factory = configuration.buildSessionFactory(registry);
            } catch (Exception e) {
                System.out.println("Проблема с созданием session factory");
                e.printStackTrace();
            }
        }
        return factory;
    }

    public static Connection connection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        if (!connection.isClosed()) {
            System.out.println("Соединение с БД установлено");
        }
        return connection;
    }
}