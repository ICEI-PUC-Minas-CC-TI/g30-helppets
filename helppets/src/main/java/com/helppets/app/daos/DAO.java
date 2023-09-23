package com.helppets.app.daos;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.Properties;

abstract public class DAO {
    private Connection connection;
    private final Properties properties = new Properties();

    private final Dotenv dotenv = Dotenv.configure()
            .directory("/private/dotenv")
            .load();

    protected DAO() {
        properties.setProperty("user", dotenv.get("DATABASE_USER"));
        properties.setProperty("password", dotenv.get("DATABASE_PASSWORD"));
        properties.setProperty("ssl", "false");
    }

    protected void makeConnection() throws SQLException {
        try {
            String databaseHost = dotenv.get("DATABASE_HOST");
            String databaseName = dotenv.get("DATABASE_NAME");

            String url = "jdbc:postgresql://".concat(databaseHost).concat("/").concat(databaseName);
            connection = DriverManager.getConnection(url, properties);
        }
        catch (Exception e) {
            System.err.println("makeConnection - Exception: " + e.getMessage());
            throw e;
        }
    }

    protected void closeConnection() throws SQLException {
        try {
            connection.close();
        }
        catch (Exception e){
            System.err.println("closeConnection - Exception: " + e.getMessage());
            throw e;
        }
    }

    protected Statement returnStatement() throws SQLException {
        return this.connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    protected PreparedStatement returnPreparedStatement(String query) throws SQLException {
        return this.connection.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

}
