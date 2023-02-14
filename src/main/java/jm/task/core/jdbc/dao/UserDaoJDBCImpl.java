package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connect = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() { // Создание таблицы User(ов)

        String createTable = "CREATE TABLE IF NOT EXISTS users("
                + "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY , "
                + "name VARCHAR(255) NOT NULL, "
                + "lastName VARCHAR(255) NOT NULL, "
                + "age TINYINT UNSIGNED)";
        try (Statement statement = connect.createStatement()) {
            statement.execute(createTable);
            connect.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        }
    }

    public void dropUsersTable() { // Удаление таблицы User(ов)
        String SQL = "DROP TABLE IF EXISTS users";
        try (Statement statement = connect.createStatement()) {
            connect.setAutoCommit(false);
            statement.executeUpdate(SQL);
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) { // Добавление User в таблицу

        String insertIntoTable = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connect.prepareStatement(insertIntoTable)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connect.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    public void removeUserById(long id) { //  Удаление User из таблицы ( по id )
        String SQL = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public List<User> getAllUsers() {  //   Получение всех User(ов) из таблицы
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM users";
        try (Statement statement = connect.createStatement(); ResultSet resultSet = statement.executeQuery(SQL);) {
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() { // Очистка содержания таблицы
        String SQL = "TRUNCATE TABLE users";
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(SQL);
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connect.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}

