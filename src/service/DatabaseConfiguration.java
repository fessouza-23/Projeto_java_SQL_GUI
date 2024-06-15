package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private static Connection connection;

    private DatabaseConfiguration() { }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                try {
                    Class.forName("org.hsqldb.jdbc.JDBCDriver");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Conecta ao banco de dados
                connection = DriverManager.getConnection("jdbc:hsqldb:mem:mydatabase", "sa", "");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
