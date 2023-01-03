package Querys;

import db.DB;
import db.DbIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BasicsQuerys {

    private static Connection connection = null;
    private static PreparedStatement statement = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");

    public static void updateData() {
        try {
            connection = DB.getConnection();
            statement = connection.prepareStatement("UPDATE seller " +
                    "SET BaseSalary = BaseSalary + ? " +
                    "WHERE " +
                    "(DepartmentId = ?)");

            statement.setDouble(1, 200.00);
            statement.setInt(2, 2);

            int rowsAffected = statement.executeUpdate();

            System.out.println("Done! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }

    public static void deleteData() {
        try {
            connection = DB.getConnection();
            statement = connection.prepareStatement("DELETE FROM department  " +
                    "WHERE " +
                    "Id = ?");

            statement.setInt(1, 4);

            int rowsAffected = statement.executeUpdate();

            System.out.println("Done! Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }

    public static void insertData() {
        try {
            statement = DB.getConnection().prepareStatement(
                    "INSERT INTO seller " +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, "Yohan Silva");
            statement.setString(2, "yohan@gmail.com");
            statement.setDate(3, new Date(sdf.parse("19/09/1994").getTime()));
            statement.setDouble(4, 3000.00);
            statement.setInt(5, 4);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                while (rs.next()){
                    System.out.println("Done! Id: " + rs.getInt(1));
                }
            } else {
                System.out.println("No rows affected" + rowsAffected);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
