package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static jm.task.core.jdbc.constants.DBConstants.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection;

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
