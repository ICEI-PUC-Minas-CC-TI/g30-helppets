package com.helppets.routerannotations.functionalities;

import com.helppets.routerannotations.annotations.Controller;
import com.helppets.routerannotations.annotations.Route;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class SetupRoutes {

    public static void setupRoutes() throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        try {
            List<Class<?>> classList = getClasssesInPackage();

            for (Class<?> controllerClass : classList) {
                String routePrefix = controllerClass.getAnnotation(Controller.class).prefix();

                Object controller = controllerClass.getDeclaredConstructor(String.class).newInstance(routePrefix);

                Method[] methods = controllerClass.getMethods();

                for (Method m : methods) {
                    try {
                        if (m.isAnnotationPresent(Route.class)) {
                            System.out.println("setupRoutes() - Initializing route: ".concat(m.getName()));

                            m.invoke(controller);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("setupRoutes() - Exception: ".concat(e.toString()));
        }
    }

    private static List<Class<?>> getClasssesInPackage() throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        InputStream controllersClassDefinition = SetupRoutes.class.getResourceAsStream("/private/controllers_definition/controllers.con");

        String controllersString = new BufferedReader(
                new InputStreamReader(
                        controllersClassDefinition, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));

        for (String c : controllersString.split("\n")) classes.add(Class.forName(c));

        return classes;
    }
}
