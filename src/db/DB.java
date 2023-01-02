package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                var properties = loadProperties();
                connection = DriverManager.getConnection(properties.getProperty("dburl"), properties);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream basePropertieFile = new FileInputStream("db.properties")) {
            properties.load(basePropertieFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
    public static void closeResultSet(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}