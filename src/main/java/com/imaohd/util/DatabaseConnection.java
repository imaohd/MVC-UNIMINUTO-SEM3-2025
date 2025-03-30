package com.imaohd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:employees.sqlite\n";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);

        // Create table if not exists
        createTableIfNotExists(conn);

        return conn;
    }

    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "rol TEXT CHECK (rol IN ('CAJERO', 'GERENTE', 'SUPERVISOR'))" +
                ")";

        try (var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
