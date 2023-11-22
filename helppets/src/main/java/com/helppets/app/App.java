package com.helppets.app;

import com.helppets.migrationsystem.migrator.Migrator;
import com.helppets.routerannotations.functionalities.SetupRoutes;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class App
{
    public static void main( String[] args ) throws InvocationTargetException, InstantiationException, IllegalAccessException,
            ClassNotFoundException, NoSuchMethodException, SQLException {
        port(8080);

        staticFiles.location("/public");

        Migrator migrator = new Migrator();

        migrator.setupMigrations();

        SetupRoutes.setupRoutes();
    }
}
