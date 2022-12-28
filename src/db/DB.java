package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            var propertieFile = loadProperties();
            connection = DriverManager.getConnection(propertieFile.getProperty("dburl"), propertieFile);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream basePropertieFile = new FileInputStream("db.properties")) {
             properties.load(basePropertieFile);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
