package com.helppets.app;

import com.helppets.routerannotations.functionalities.SetupRoutes;

import java.lang.reflect.InvocationTargetException;

public class App
{
    public static void main( String[] args ) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        SetupRoutes.setupRoutes();
    }
}
