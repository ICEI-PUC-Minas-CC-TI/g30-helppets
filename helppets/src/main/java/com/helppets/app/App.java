package com.helppets.app;

import com.helppets.routerannotations.functionalities.SetupRoutes;

import java.lang.reflect.InvocationTargetException;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class App
{
    public static void main( String[] args ) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        port(8080);

        staticFiles.location("/public");

        SetupRoutes.setupRoutes();
    }
}
