package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "roott";

    public static Connection connection;

    public Util() {

    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            System.out.println("Connection Ok");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }
}
