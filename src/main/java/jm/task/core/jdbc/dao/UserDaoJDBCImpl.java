package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {

        String query = "create table if not exists Users " +
                "(id integer primary key auto_increment not null, " +
                " name varchar(255), " +
                " last_name varchar(255), " +
                " age integer)";

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        String query = "drop table if exists Users ";

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String query = "insert Users (name, last_name, age) values ('" + name + "', '" + lastName + "', '" + age + "')";

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        String query = "delete from Users where id = ?";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        String query = "select * from Users";

        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
                System.out.println(user);
            }
            return users.size() > 0 ? users : Collections.emptyList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {

        String query = "truncate table Users";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
