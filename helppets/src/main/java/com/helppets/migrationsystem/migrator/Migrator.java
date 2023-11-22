package com.helppets.migrationsystem.migrator;

import com.helppets.app.daos.DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Migrator extends DAO {
    private final Logger logger = LoggerFactory.getLogger(Migrator.class);
    public void setupMigrations() throws SQLException {
        InputStream migListStream = Migrator.class.getResourceAsStream("/private/migrations/");

        String migList = new BufferedReader(
                new InputStreamReader(
                        migListStream, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));

        this.makeConnection();

        logger.info("setupMigrations() - migList: {}", migList);

        for (String migrationName: migList.split("\n")) {
            try {
                InputStream migrationStream = Migrator.class.getResourceAsStream("/private/migrations/".concat(migrationName));

                String migration = new BufferedReader(
                        new InputStreamReader(
                                migrationStream, StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining("\n")).replaceAll("\n", "");

                for (String instrucion : migration.split(";")) {
                    logger.info("setupMigrations() - Migration: {}", instrucion);

                    this.returnStatement().execute(instrucion);
                }
            }
            catch (Exception e) {
                logger.error("setupMigrations() - Exception: {}", e.getMessage());
            }
        }

        this.closeConnection();
    }

    @Override
    public Object insert(Object object) throws SQLException {
        return null;
    }

    @Override
    public Object getById(int id) throws SQLException {
        return null;
    }

    @Override
    public Object deleteById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<?> selectAllWithLimiter(int limiter) throws SQLException {
        return null;
    }

    @Override
    public Object updateById(int id, Object object) throws SQLException {
        return null;
    }

    @Override
    protected Object parseRowToDto(ResultSet resultSet) throws SQLException {
        return null;
    }
}
