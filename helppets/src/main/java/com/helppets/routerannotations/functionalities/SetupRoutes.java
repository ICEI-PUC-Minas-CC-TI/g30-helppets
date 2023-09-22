package com.helppets.routerannotations.functionalities;

import com.helppets.routerannotations.annotations.Controller;
import com.helppets.routerannotations.annotations.Route;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetupRoutes {
    public static void  setupRoutes() throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        String currentPackage = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getCanonicalName();

        List<Class<?>> classList = getClasssesInPackage("helppets.src.main.java." + currentPackage);

        for (Class<?> controllerClass : classList) {
            Object controller = controllerClass.newInstance();

            Method[] methods = controllerClass.getMethods();

            for (Method m : methods) {
                try {
                    if (m.isAnnotationPresent(Route.class)) {
                        m.invoke(controller);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    private static List<Class<?>> getClasssesInPackage(String currentPackage) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        String[] packages = currentPackage.split("\\.");

        StringBuilder controllerPath = new StringBuilder();
        StringBuilder controllerPackage = new StringBuilder();

        for (int x = 0; x < packages.length - 1; x++) {
            controllerPath.append(packages[x]).append("/");

            if (x > 3) {
                controllerPackage.append(packages[x]).append(".");
            }
        }

        controllerPath.append("controllers");
        controllerPackage.append("controllers");

        File dir = new File(controllerPath.toString());

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (Objects.nonNull(files)) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        String className = controllerPackage + "." + file.getName().substring(0, file.getName().length() - 5);
                        Class<?> controllerClass = Class.forName(className);

                        if (controllerClass.isAnnotationPresent(Controller.class)) {
                            classes.add(controllerClass);
                        }
                    }
                }
             }
        }

        return classes;
    }
}
