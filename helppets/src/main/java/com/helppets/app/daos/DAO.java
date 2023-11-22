package com.helppets.app.daos;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Properties;

abstract public class DAO {
    private Connection connection;
    private final Properties properties = new Properties();
    protected Logger logger;

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
            String schemaName = dotenv.get("DATABASE_SCHEMA");
            String databasePort = dotenv.get("DATABASE_PORT");

            String url = "jdbc:postgresql://"
                    .concat(databaseHost)
                    .concat(":")
                    .concat(databasePort)
                    .concat("/")
                    .concat(databaseName)
                    .concat("?currentSchema=")
                    .concat(schemaName);

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

    public abstract Object insert(Object object) throws SQLException;
    public abstract Object getById(int id) throws SQLException;
    public abstract Object deleteById(int id) throws SQLException;
    public abstract List<?> selectAllWithLimiter(int limiter) throws SQLException;
    public abstract Object updateById(int id, Object object) throws SQLException;
    protected abstract Object parseRowToDto(ResultSet resultSet) throws SQLException;
}
