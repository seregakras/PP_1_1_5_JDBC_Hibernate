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

import static jm.task.core.jdbc.constants.DBConstants.*;

public class Util {
    // реализуйте настройку соединения с БД
    private static Connection connection;

    public static SessionFactory getSessionFactory() {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://" + DB_HOST + ":" + PORT + "/" + DB);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "none");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                return configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                return DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + PORT + "/" + DB,
                                                   USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Connection failed...");
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void closeConnection()  {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection is closed!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
